#!/usr/bin/env python
# -*- coding: utf-8 -*-

import sys
from urllib2 import urlopen
import bs4 as BeautifulSoup
import time

import re


def request(url):
    cnt = 0
    res = False
    while res == False and cnt < 10:
        try:
            j = urlopen(url).read()
            res = True
        except Exception as e:
            cnt += 1
            print e.message
            print e.args

            #en cas de probleme attendre 20 secondes
            time.sleep(20)

    if cnt >= 10:
        return -1
    else:
        return BeautifulSoup.BeautifulSoup(j,"lxml")

#obtiens la liste des accords d'une url
def getAccord(url):
    s = request(url)
    if s != -1:
        accords = s.find_all('a', attrs={'class':'ac'})

        accordList = []
        for accord in accords:
            accordList.append(accord.text.encode('ascii', 'ignore'))

        return accordList
    else:
        return []





alphabet = ["9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]
cntNewSong = 0

with open("Lettre parsee/newSong.txt", "w") as fUpdate:

    #pour tous les artistes (de A a Z + 9)
    for lettre in alphabet:
        print lettre
        urlsFromFile = []
        with open("Lettre parsee/"+lettre+".txt", "r+") as f:
            for l in f.readlines():
                urlsFromFile.append(l.split(",")[2])


            #recuperation de la liste des url des artistes
            url = "http://www.boiteachansons.net/Partitions/index.php?artiste=" + str(lettre)
            s=request(url)
            if s != -1:

                artistes = s.find_all('li', attrs={'class':'liElementLstPartitions'})
                for a in artistes:
                    url = a.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")

                    #recuperation des urls de chaque chanson de chaque artiste
                    s = request(url)
                    if s != -1:
                        songs = s.find_all('li', attrs={'class':'liElementLstPartitions'})
                        for s in songs:
                            url = s.find('a', attrs={'class':'aLiensChansons'})['href'].replace("..","http://www.boiteachansons.net")
                            if url not in urlsFromFile:
                                accord = getAccord(url)
                                if len(accord) != 0:
                                    cntNewSong +=1
                                    print "nouvelle chanson : "+url
                                    urlSplit = url.replace("-"," ").replace(".php","").split("/")
                                    Tsong=[urlSplit[-2],urlSplit[-1], url, accord]
                                    f.writelines(str(Tsong).replace("'", "")[1:-1].replace(", ", ",")+"\n")
                                    fUpdate.writelines(str(Tsong).replace("'", "")[1:-1].replace(", ", ",")+"\n")

            f.close()
    fUpdate.close()




print "il y a eut "+str(cntNewSong)+" nouvelle(s) chanson(s)"
