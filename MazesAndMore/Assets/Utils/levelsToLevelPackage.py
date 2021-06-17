import glob
#listaMetas = glob.glob("C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Data/Levels/ice_floor/*.meta")
#ruta1 = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/ScriptableObjects/IceGroup.asset'

listaMetas = glob.glob("C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Data/Levels/classic/*.meta")
ruta1 = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/ScriptableObjects/ClassicGroup.asset'

idClassicGroupRead = open(ruta1, 'r')
contenidoDestino = idClassicGroupRead.readlines()

length = len(listaMetas)

contenidoDestinoAux = contenidoDestino.copy()

j = 0

while j < length:    
    i = 0
    while i < length:

        #ice_floor
        #string = listaMetas[i][107:109]

        #classic
        string = listaMetas[i][105:108]
        characters = ".j"
        for x in range(len(characters)):
            string = string.replace(characters[x],"")

        if (j+1 == int(string)):
            idMeta = open(listaMetas[i], 'r')
            plantilla = idMeta.readlines()

            aux = plantilla[1].rstrip();

            lineToInsert =  '  - {fileID: 4900000, ' + aux + ', type: 3} \n'
            contenidoDestinoAux.insert(15+j, lineToInsert)
            i = length
            idMeta.close
        i+=1
    j+=1

idClassicGroupWrite = open(ruta1, 'w')

for item in contenidoDestinoAux:
    idClassicGroupWrite.write(item)

idClassicGroupRead.close
idClassicGroupWrite.close