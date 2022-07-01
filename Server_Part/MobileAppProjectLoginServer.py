# Mobile App Programming Project
# Park Gyeol

##############################################
#
#                RDS Part
#
##############################################

from sqlalchemy import create_engine
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String


USER_ID = "SECRET"
PW = "SECRET"
URL = "SECRET"
PORT = "SECRET"
DB = "postgres"


# (1) Connect to our DB instance
engine = create_engine("postgresql://{}:{}@{}:{}/{}".format(USER_ID, PW, URL, PORT, DB))


# (2) Make session to communicate with RDS instance
# Why AutoCommit is False?
#   => 데이터 변경 작업 때 여러 줄의 SQL 쿼리 사용 시 한 번에 반영시키기 위해서
db_session = scoped_session(sessionmaker(autocommit=False, autoflush=False, bind=engine))


# (3) Declare Base class (A base class stores a catalog of classes and mapped tables in the system)
Base = declarative_base()
Base.query = db_session.query_property()


# (4) Declare Data Class!
# ORM이므로 사용하고 싶은 테이블을 class로써 정의.
class User(Base):
    __tablename__ = 'user_1'
    id = Column(String(20), primary_key=True)
    password = Column(String(20), unique=False)
    name = Column(String(20), unique=False)
    phone_num = Column(String(12), unique=False)

    def __init__(self, id=None, password=None, name=None, phone_num=None):
        self.id = id
        self.password = password
        self.name = name
        self.phone_num = phone_num

    def __repr__(self):
        return f'<User {self.id!r}>'


# 5) Create all tables (drop_all() method delete all tables)
Base.metadata.create_all(bind=engine)


##############################################
#
#                Flask Part
#
##############################################

from flask import Flask
from flask import request
from flask import jsonify
from werkzeug.serving import WSGIRequestHandler
import json

WSGIRequestHandler.protocol_version = "HTTP/1.1"

app = Flask(__name__)


@app.route("/adduser", methods=['POST'])
def add_user():
    content = request.get_json(silent=True)
    id = content["id"]
    name = content["name"]
    password = content["password"]
    phone_num = content["phone_num"]

    if db_session.query(User).filter_by(id=id).first() is not None:
        return jsonify(success=False, result_detail="id_duplicated")

    if name == "":
        return jsonify(success=False, result_detail="name_empty")

    if password == "":
        return jsonify(success=False, result_detail="password_empty")

    if phone_num == "":
        return jsonify(success=False, result_detail="phonenum_empty")


    us = User(id=id, password=password, name=name, phone_num=phone_num)
    db_session.add(us)
    db_session.commit()
    return jsonify(success=True, result_detail="")


@app.route("/getuser", methods=['POST'])
def get_user():
    content = request.get_json(silent=True)
    id = content["id"]

    if db_session.query(User).filter_by(id=id).first() is None:
        return jsonify(success=False, name="no_user", phone_num="no_user")

    query = db_session.query(User).filter_by(id=id).first()
    name_ = query.name
    phone_num_ = query.phone_num

    return jsonify(success=True, name= name_, phone_num=phone_num_)


@app.route("/getAll", methods=['POST'])
def get_all_users():
    #content = request.get_json(silent=True)

    query = db_session.query(User).all()
    ret = []
    for i in query:
        result_element = {}
        result_element["id"] = i.id
        result_element["password"] = i.password
        result_element["name"] = i.name
        result_element["phone_num"] = i.phone_num
        ret.append(result_element)

    return jsonify(ret), 200


@app.route("/deluser", methods=['POST'])
def del_user():
    content = request.get_json(silent=True)
    id = content["id"]

    if db_session.query(User).filter_by(id=id).first() is None:
        return jsonify(success=False, result_detail="no_user")
    else:
        db_session.query(User).filter_by(id=id).delete()
        db_session.commit()
        return jsonify(success=True, result_detail="success")


@app.route("/login", methods=['POST'])
def login():
    content = request.get_json(silent=True)
    id = content["id"]
    password = content["password"]

    check = False
    error_message = "wrong_id"
    result = db_session.query(User).all()
    for i in result:
        if i.id == id:
            if i.password == password:
                error_message = ""
                check = True
            else:
                error_message = "wrong_password"

    return jsonify(success=check, result_detail=error_message)


if __name__ == "__main__":
    app.run(host='localhost', port=8888)