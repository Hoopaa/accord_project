#!/usr/bin/env python
# -*- coding: utf-8 -*-

#permet de récuperer l'url YouTube d'une chanson du site Boite a Chanson

import sys
from urllib2 import urlopen
import bs4 as BeautifulSoup
import time

import re

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
            time.sleep(20)

    return BeautifulSoup.BeautifulSoup(j,"lxml")


def getYoutubeURLfromboiteachansonsURL(urlBaC):


    recherche = urlBaC.replace("http://www.boiteachansons.net/Partitions/","").replace(".php","").replace("/","+").replace("-","+")

    res = request("https://www.youtube.com/results?search_query="+recherche)

    print "https://www.youtube.com/results?search_query="+recherche

    pos = str(res).find("/watch?")

    print "www.youtube.com"+str(res)[pos:pos+20]

getYoutubeURLfromboiteachansonsURL("http://www.boiteachansons.net/Partitions/Kain/Adam-et-Eve.php")
