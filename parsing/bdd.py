import sqlite3

def createDB():
    conn = sqlite3.connect('example.db')
    c = conn.cursor()
    c.execute('''CREATE TABLE artiste
                 (name text, url text, song int)''')
    c.execute('''CREATE TABLE song
                 (id int, name text, url text, accord text)''')

    conn.commit()
    conn.close()

def insertSong(id,  name , url , accord):
    conn = sqlite3.connect('example.db')
    c = conn.cursor()
    c.execute("INSERT INTO song VALUES ({0},'{1}','{2}','{3}')".format(id,name,url,accord))
    conn.commit()
    conn.close()

def insertArtiste(name , url , song):
    conn = sqlite3.connect('example.db')
    c = conn.cursor()
    c.execute("INSERT INTO artiste VALUES ({0},'{1}',{2})".format(name, url, song))
    conn.commit()
    conn.close()
