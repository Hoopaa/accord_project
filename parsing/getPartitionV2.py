#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
from urllib2 import urlopen
import bs4 as BeautifulSoup

import re


class output:

    def __init__(self, fileName):
        self.file = open(fileName,"w")

    def insertSong(self, song, artiste):
        Tsong=[artiste.name, song.title, song.url, song.accord]
        self.file.writelines(str(Tsong).replace("'", "")+"\n")

    def close(self):
        self.file.close()


class artiste:

    name = ""
    url = ""
    songs = []

    def __init__(self, url):
        self.name = url.split("/")[-1].replace(".php","").replace("-", " ")
        self.url = url
        self.songs = []

    def addSong(self, song):
        self.songs.append(song)

class song:

    title = ""
    url = ""
    accord = []

    def __init__(self, url, accord):
        self.title = url.split("/")[-1].replace(".php","").replace("-", " ")#re.sub('[^A-Za-z0-9 '',]+', '', name).encode('ascii', 'ignore')
        self.url = url
        self.accord = accord




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
        url = a.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")

        artistList.append(artiste(url))

    return artistList


def getSongsList(artiste):
    #recuperation de la liste des songs pour l artiste
    url = artiste.url
    j = urlopen(url).read()

    s = BeautifulSoup.BeautifulSoup(j,"lxml")

    songs = s.find_all('li', attrs={'class':'liElementLstPartitions'})

    for s in songs:
        name = s.find_all('span', attrs={'class':'sLstPart_TitreChanson'})[0].text
        url = s.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")
        accord = getAccord(url)
        artiste.addSong(song(url, accord))


def getAccord(url):
    j = urlopen(url).read()
    s = BeautifulSoup.BeautifulSoup(j,"lxml")

    accords = s.find_all('a', attrs={'class':'ac'})

    accordList = []
    for accord in accords:
        accordList.append(accord.text.encode('ascii', 'ignore'))

    return accordList














alphabet = ["9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]

for lettre in alphabet:
    sortie = output(lettre + ".txt")
    print lettre
    b = getArtistList(lettre)
    for i in range(len(b)):
        print"     "+str(b[i].name)
        getSongsList(b[i])
        for s in b[i].songs:
            sortie.insertSong(s,b[i])


    sortie.close()
