import requests
import json


def send_fcm_notification(ids, title, body):
    # fcm 푸시 메세지 요청 주소
    url = 'https://fcm.googleapis.com/fcm/send'

    # 인증 정보(서버 키)를 헤더에 담아 전달
    headers = {
        'Authorization': 'key=AAAArQ_rdz8:APA91bH7HNoENZkEakdZydTqmSE0kVZifY_xor0V9uzz-pIpFA4XbYxmX18HRLwol0urhBHTs4Cwi-r7qoO1_t3Cft_K5i8GmTZM_TH-T1tUO41xSe4TaBurrrcNlOc4uo6LOzl9WaCW',
        'Content-Type': 'application/json; UTF-8'
    }

    # 보낼 내용과 대상을 지정
    content = {
        'registration_ids': ids,
        'notification': {
            'title': title,
            'body': body
        }
    }

    # json 파싱 후 requests 모듈로 FCM 서버에 요청
    requests.post(url, data=json.dumps(content), headers=headers)
    print('hi\n\n\n\n\n\n')

