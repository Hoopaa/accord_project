#! /usr/bin/env python
#-*- coding: utf-8 -*-

import sys
import time
import logging
import os
import json
import unicodedata
from urllib2 import urlopen
import bs4 as BeautifulSoup

import re

file_path = os.path.dirname(__file__)
sys.path.insert(0, file_path)

from flask import Flask, render_template, jsonify, Response, request

app = Flask(__name__,static_url_path='')



@app.route('/youtubeUrl/<url>',strict_slashes=False)
def get_youtube(url):
        return getYoutubeURLfromboiteachansonsURL(url)

@app.route('/toTxt/<url>',strict_slashes=False)
def get_txt(url):
    getTxtSong2(url)
    return getTxtSong(url)



def getTxtSong(url):
    print "DEBUG"
    print url
    #http://www.boiteachansons.net/Txt/Jimmy-C-Newman/Lache-pas-la-patate.txt
    url = url.replace("_","/")
    url = "http://www.boiteachansons.net/Txt/"+url+".txt"
    print url
    res = request(url)
    rl =  str(res)[88]
    string = str(res)
    string = string.replace(rl,"<br />")
    #print res
    return str(res)

def getTxtSong2(url):

        url = url.replace("_","/")
        url = "http://www.boiteachansons.net/Partitions/"+url+".php"
        res = request(url)
        s = res.find_all('div', attrs={'class':'pLgn'})
        resultat = ""
        for i in s:
            print i.text



def request(url):
    res = False
    while res == False:
        try:
            j = urlopen(url).read()
            res = True
        except Exception as e:
            print e.message
            print e.args

            #en cas de probleme attendre 20 secondes
            time.sleep(5)

    return BeautifulSoup.BeautifulSoup(j,"lxml")


def getYoutubeURLfromboiteachansonsURL(urlBaC):
    recherche = urlBaC.replace("http://www.boiteachansons.net/Partitions/","").replace(".php","").replace("/","+").replace("-","+")
    res = request("https://www.youtube.com/results?search_query="+recherche)
    print "https://www.youtube.com/results?search_query="+recherche
    pos = str(res).find("/watch?")
    return "www.youtube.com"+str(res)[pos:pos+20]


if __name__ == '__main__':
    try:
        app.run(host='0.0.0.0', debug=False, use_reloader=False)

    except KeyboardInterrupt:
        print "1"
