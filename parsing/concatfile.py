filenames = ['9.txt', 'A.txt', 'B.txt']
temp = ["9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"]

with open('all.txt', 'w') as outfile:
    for fname in filenames:
        with open(fname) as infile:
            for line in infile:
                outfile.write(line)

'''
with open('all.txt', 'w') as outfile:
    for fname in temp:
        with open(fname+".txt") as infile:
            for line in infile:
                outfile.write(line)'''
