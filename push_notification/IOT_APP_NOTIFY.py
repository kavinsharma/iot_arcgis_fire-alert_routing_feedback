
from pyfcm import FCMNotification
import pyrebase

push_service = FCMNotification(api_key="AAAAan2iGkY:APA91bEyBbuW6BOHDeuEVOFXa_kFldD-jrowHP6T1B-ZM3PwJsu1d6qClE4tAoGt0hya89VbBBqaq-2H-ErYOK6Bhsdy2f6Zj_DNLUKwWuVZIXxSqzffGJY4iDMYjSiyvMTeaF1u9oup")


config = {
"apiKey": "AIzaSyAhw2kHSFjIm4L3ZFbmhPCpw5mScEksDc8",
"authDomain": "esri-eea51.firebaseapp.com",
"databaseURL": "https://esri-eea51.firebaseio.com",
"projectId": "esri-eea51",
"storageBucket": "esri-eea51.appspot.com",
"messagingSenderId": "457374308934"
};

firebase = pyrebase.initialize_app(config)
db = firebase.database()

message_title = "Fire Alert"
message_body = db.child("address").get().val()
message_body = "Fire Breakeage at:"+ str(message_body)
#registration_id = db.child("message").get().val()
registration_id2 = db.child("message1").get().val()
def stream_handler(post):
	print(post)
	if (post['data'] is 1):
	#	result = push_service.notify_single_device(registration_id=registration_id2, message_title=message_title, message_body=message_body, sound="default")
		result = push_service.notify_single_device(registration_id=registration_id2, message_title=message_title, message_body=message_body, sound="default")

		print (result)
my_stream = db.child("fire_sensor_status").stream(stream_handler, None)

