#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
import time
import logging
import json
from urllib2 import urlopen
import bs4 as BeautifulSoup
import unicodedata
import sqlite3
import re
from pymongo import Connection

class dbMongo:

    def __init__(self):
        self.connection = Connection('127.0.0.1', 27017)
        self.db = self.connection.mydatabase2
        self.db['song'].drop()
        self.db['artiste'].drop()


    def insertSongMongo(self, song, id):
        Tsong={'id':id, }
        self.db['song'].insert(Tsong)

    def insertArtisteMongo(self, artiste, id):
        Tartiste={'id':id, 'name':artiste.name, 'link':artiste.link, 'nbSong':artiste.nbSong}
        self.db['artiste'].insert(Tartiste)

def createDB():
    connexion = sqlite3.connect('example.db')
    c = connexion.cursor()
    c.execute('''CREATE TABLE artiste
                 (id int, name text, url text, song int)''')
    c.execute('''CREATE TABLE song
                 (id int, name text, url text, accord text)''')

    connexion.commit()
    return connexion

def insertSong(song, id, connexion):
    connexion = sqlite3.connect('example.db')
    c = connexion.cursor()
    c.execute("INSERT INTO song VALUES ({0},'{1}','{2}','{3}')".format(id, song.title, song.link,"jklkj"))
    connexion.commit()

def insertArtiste(artiste, id, connexion):
    connexion = sqlite3.connect('example.db')
    c = connexion.cursor()
    c.execute("INSERT INTO artiste VALUES ({0}, '{1}','{2}',{3})".format(id, artiste.name, artiste.link, artiste.nbSong))
    connexion.commit()



class artiste:

    name = ""
    link = ""
    nbSong = 0
    songs = []

    def __init__(self, name, link, nbSong):
        self.name = re.sub('[^A-Za-z0-9 '',]+', '', name)
        self.link = link
        self.nbSong = nbSong

    def addSong(self, song):
        self.songs.append(song)

class song:

    title = ""
    link = ""
    accord = []

    def __init__(self, name, link, accord):
        self.title = re.sub('[^A-Za-z0-9 '',]+', '', name)
        self.link = link
        self.accord = accord

    def __str__(self):
        return self.name + " : " + str(self.accord)




def getArtistList(firstLettre):
    url = "http://www.boiteachansons.net/Partitions/index.php?artiste=" + str(firstLettre)
    j = urlopen(url).read()

    s = BeautifulSoup.BeautifulSoup(j,"lxml")

    #recuperation de la liste des artistes
    artists = s.find_all('li', attrs={'class':'liElementLstPartitions'})

    #recuperation des information pour chaque artiste (nom, lien, nb chanson)
    artistList = []
    for a in artists:
        name = "".join(a.find_all('td')[-1].text.split("(")[:-1])[:-1]
        link = a.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")
        nbSong = int(a.find_all('td')[-1].text.split("(")[-1].split("partition")[0])

        artistList.append(artiste(name, link, nbSong))

    return artistList


def getSongsList(artiste):
    #recuperation de la liste des songs pour l artiste
    url = artiste.link
    j = urlopen(url).read()

    s = BeautifulSoup.BeautifulSoup(j,"lxml")

    songs = s.find_all('li', attrs={'class':'liElementLstPartitions'})

    for s in songs:
        name = s.find_all('span', attrs={'class':'sLstPart_TitreChanson'})[0].text
        link = s.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")
        accord = getAccord(link)
        artiste.addSong(song(name, link, accord))


def getAccord(link):
    j = urlopen(link).read()

    s = BeautifulSoup.BeautifulSoup(j,"lxml")

    accord = s.find_all('a', attrs={'class':'ac'})

    accordList = []
    for a in accord:
        accordList.append(a.text)


    return accordList



#connexion = createDB()
bdd=dbMongo()
temp = ["9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]
temp = ["9"]
a=[]
cnt = 0
id=0
for t in temp:
    print t
    b = getArtistList(t)
    for i in range(len(b)):
        getSongsList(b[i])
        #insertArtiste(b[i], id, connexion)
        bdd.insertArtisteMongo(b[i],id)
        for s in b[i].songs:
            bdd.insertSongMongo(s,id)
            #insertSong(s, id, connexion)
        id+=1
    a+=b
#connexion.close()
'''print "artiste :"+str(len(a))
for aa in a:
    cnt +=aa.nbSong
print cnt'''
'''a = getArtistList("9")
getSongsList(a[1])

for b in a[1].songs:
    i=0
    print b.name
    for aa in b.accord:
        i+=1
        print aa+" "+str(i)
    print b

print s[0].link
print s[0].name
print s[0].accord'''
