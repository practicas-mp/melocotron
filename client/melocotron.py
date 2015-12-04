#!/usr/bin/python
#! -*- coding: utf-8 -*-

import socket

TCP_PORT = 1338
TCP_IP = '127.0.0.1'

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


def main():
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((TCP_IP, TCP_PORT))
    sock.send(str(CODES['OP_LIST_RESOURCES']))
    response = sock.recv(1024)

    print response

    sock.send(str(CODES['OP_ACCESS_RESOURCE']) + 'lol')

    response = sock.recv(1024)

    print response


if __name__ == '__main__':
    main()
