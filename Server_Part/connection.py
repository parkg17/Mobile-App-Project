# S3 Connection Functions
import boto3
from config import AWS_ACCESS_KEY, AWS_SECRET_ACCESS_KEY, \
                   AWS_S3_BUCKET_REGION, AWS_S3_BUCKET_NAME

def s3_connection():
    try:
        s3 = boto3.client(
            service_name="s3",
            region_name=AWS_S3_BUCKET_REGION,
            aws_access_key_id=AWS_ACCESS_KEY,
            aws_secret_access_key=AWS_SECRET_ACCESS_KEY
        )
    except Exception as e:
        print(e)
        exit(0)
    else:
        print("s3 bucket connected!")
        return s3


def s3_upload_file(s3, bucket, filepath, access_key):
    try:
        s3.upload_file(filepath, bucket, access_key)
    except Exception as e:
        print(e)
        return False
    return True


def s3_download_file(s3, bucket, object_name, file_name):
    try:
        s3.download_file(bucket, object_name, file_name)
    except Exception as e:
        print(e)
        return False
    return True
