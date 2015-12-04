#!/usr/bin/python
from collections import namedtuple
import socket


CODES = {
    'OP_AUTH': 10,
    'OP_LIST_RESOURCES': 20,
    'OP_LIST_SUBRESOURCES': 30,
    'OP_ACCESS_RESOURCE': 40,
    'OP_ACCESS_SUBRESOURCE': 50,
    'OP_BYE': 60,
    'AUTH_VALID': 110,
    'AUTH_INVALID': 510,
    'LIST_RESOURCES': 120,
    'LIST_SUBRESOURCES': 130,
    'RESOURCE_NOT_FOUND': 530,
    'SHOW_RESOURCE': 140,
    'SHOW_SUBRESOURCE': 150,
    'SUBRESOURCE_NOT_FOUND': 550,
    'UNKNOWN_OPERATION': 570,
    'KTHX_BYE': 160,
}

Message = namedtuple('Message', ['op_code', 'body'])

def parse_raw_message(msg):
    op_code = int(msg[:3])
    body = msg[3:]

    return Message(op_code=op_code, body=body)

class MeloClient(object):
    def __init__(self, host, port):
        self.sock = socket.socket()
        self.sock.connect((host, port))


    def send_command(self, name, body=""):
        self.sock.send(str(CODES[name]) + body + '\n')

    def receive_response(self):
        content = ""

        while content.endswith('\n') == False:
            new_content = self.sock.recv(10)
            if new_content != chr(0):
                content += new_content
        content = filter(lambda c: c != chr(0), content)

        return parse_raw_message(content)

    def auth(self, user, password):
        self.send_command("OP_AUTH", user + ":" + password)
        return self.receive_response()

    def list_resources(self):
        self.send_command("OP_LIST_RESOURCES")
        return self.receive_response()

    def access_resource(self, resource_name):
        self.send_command("OP_ACCESS_RESOURCE", resource_name)
        out = self.receive_response()
        body = out.body.replace("\\n", "\n")
        print body
        outp = Message(op_code=out.op_code, body=body)
        return outp 


