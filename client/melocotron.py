#!/usr/bin/python
#! -*- coding: utf-8 -*-

from melo import MeloClient
TCP_PORT = 1338
TCP_IP = '127.0.0.1'



def main():
    client = MeloClient(TCP_IP,TCP_PORT )

    print client.auth("bbb", "ccc")
    print client.list_resources()

    print client.access_resource('lol')

if __name__ == '__main__':
    main()
