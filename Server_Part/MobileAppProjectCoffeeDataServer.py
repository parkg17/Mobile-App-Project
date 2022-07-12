# Mobile App Programming Project
# Park Gyeol
# Last Update : 2022-07-08


import io, json, os
from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
from config import AWS_ACCESS_KEY, AWS_SECRET_ACCESS_KEY, \
                   AWS_S3_BUCKET_REGION, AWS_S3_BUCKET_NAME
from connection import s3_connection, s3_upload_file, s3_download_file
from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String, Float
from config import USER_ID, PW, URL, PORT, DB

WSGIRequestHandler.protocol_version = "HTTP/1.1"

# (1) Connect to our DB instance
engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER_ID, PW, URL, PORT, DB), encoding='utf8')


# (2) Make session to communicate with RDS instance
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))

# (3) Declare Base class (A base class stores a catalog of classes and mapped tables in the system)
Base = declarative_base()
Base.query = db_session.query_property()

# (4) Declare Data Class!
class Coffee(Base):
    __tablename__ = 'Coffee_items'
    id = Column(String(30), primary_key=True)
    name = Column(String(30), unique=False)
    stock = Column(Integer, unique=False)
    price = Column(Float, unique=False)
    rating = Column(Float, unique=False)
    grade = Column(String(20), unique=False)
    expiredDate = Column(String(10), unique=False)
    description = Column(String(100), unique=False)

    def __init__(self, id=None, name=None, stock=None, price=None,
                 rating=None, grade=None, expiredDate=None, description=None):
        self.id = id
        self.name = name
        self.stock = stock
        self.price = price
        self.rating = rating
        self.grade = grade
        self.expiredDate = expiredDate
        self.description = description

    def __repr__(self):
        return f'<User {self.id!r}>'


# 5) Create all tables (drop_all() method delete all tables)
Base.metadata.create_all(bind=engine)

app = Flask(__name__)
s3 = s3_connection()

#############################################################
#
#            S3 이미지 업로드 / 다운로드 코드
#
#############################################################

@app.route("/upload", methods=['GET'])
def upload():
    current = os.getcwd()
    os.chdir(os.getcwd() + "./unuploaded")
    unuploaded_path = os.getcwd()
    uploaded_path = current + "./uploaded"
    result = {}
    filenames = os.listdir(unuploaded_path)
    for filename in filenames:
        ret = s3_upload_file(s3, AWS_S3_BUCKET_NAME, filename, filename)
        if not ret:
            result["detail"] = "false"
            return jsonify(result), 200
            break

    for filename in filenames:
        os.replace(os.path.join(unuploaded_path, filename), os.path.join(uploaded_path, filename))

    result["detail"] = "true"
    return jsonify(ret), 200


@app.route("/download", methods=['GET'])
def download():
    file_name = request.args.get('filename')
    ret = {}
    ret["link"] = "https://{}.s3.{}.amazonaws.com/{}".format(
        AWS_S3_BUCKET_NAME, AWS_S3_BUCKET_REGION, file_name)
    return jsonify(ret), 200


#############################################################
#
#                커피 데이터 SQL 관련 코드
#
#############################################################


@app.route("/addcoffeeByText", methods=['POST'])
def add_coffee_by_text():
    contents = request.get_json(silent=True)
    isExecute = False

    for content in contents:
        id = content["id"]
        name = content["name"]
        stock = content["stock"]
        price = content["price"]
        rating = content["rating"]
        grade = content["grade"]
        expiredDate = content["expiredDate"]
        description = content["description"]

        if db_session.query(Coffee).filter_by(id=id).first() is None and id != "NULL":
            new_coffee_item = Coffee(id=id, name=name, stock=stock, price=price, rating=rating,
                                     grade=grade, expiredDate=expiredDate, description=description)

            db_session.add(new_coffee_item)
            isExecute = True

    if isExecute:
        db_session.commit()

    return jsonify(success=True, result_detail="")


@app.route("/addcoffee", methods=['POST'])
def add_coffee():
    content = request.get_json(silent=True)
    id = content["id"]
    name = content["name"]
    stock = content["stock"]
    price = content["price"]
    rating = content["rating"]
    grade = content["grade"]
    expiredDate = content["expiredDate"]
    description = content["description"]

    if db_session.query(Coffee).filter_by(id=id).first() is not None:
        return jsonify(success=False, result_detail="id_duplicated")
    elif id == "NULL":
        return jsonify(success=False, result_detail="id_prohibition")

    new_coffee_item = Coffee(id=id, name=name, stock=stock, price=price, rating=rating,
                             grade=grade, expiredDate=expiredDate, description=description)

    db_session.add(new_coffee_item)
    db_session.commit()
    return jsonify(success=True, result_detail="")


@app.route("/getcoffee", methods=['POST'])
def get_coffee():
    content = request.get_json(silent=True)
    id = content["id"]

    if db_session.query(Coffee).filter_by(id=id).first() is None:
        return jsonify(id="NULL", name="", stock=0, price=0.0, rating=0.0,
                       grade="", expiredDate="", description="")

    query = db_session.query(Coffee).filter_by(id=id).first()
    id = query.id
    name = query.name
    stock = query.stock
    price = query.price
    rating = query.rating
    grade = query.grade
    expiredDate = query.expiredDate
    description = query.description

    return jsonify(id=id, name=name, stock=stock, price=price, rating=rating,
                   grade=grade, expiredDate=expiredDate, description=description)


@app.route("/getAll", methods=['POST'])
def get_all_coffees():
    query = db_session.query(Coffee).all()
    ret = []
    for i in query:
        result_element = {}
        result_element["id"] = i.id
        result_element["name"] = i.name
        result_element["stock"] = i.stock
        result_element["price"] = i.price
        result_element["rating"] = i.rating
        result_element["grade"] = i.grade
        result_element["expiredDate"] = i.expiredDate
        result_element["description"] = i.description
        ret.append(result_element)

    return jsonify(ret), 200


@app.route("/delcoffee", methods=['POST'])
def del_coffee():
    content = request.get_json(silent=True)
    id = content["id"]

    if db_session.query(Coffee).filter_by(id=id).first() is None:
        return jsonify(success=False, result_detail="no_user")
    else:
        db_session.query(Coffee).filter_by(id=id).delete()
        db_session.commit()
        return jsonify(success=True, result_detail="success")


@app.route("/delAll", methods=['POST'])
def del_coffee_all():
    db_session.query(Coffee).delete()
    db_session.commit()
    return jsonify(success=True, result_detail="success")

if __name__ == "__main__":
    app.run(host='localhost', port=8080)
