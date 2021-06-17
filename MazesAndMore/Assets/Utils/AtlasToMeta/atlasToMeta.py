rutaOrigen = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Images/Prueba metas/texture_hd.atlas'
idatlas = open(rutaOrigen, 'r')
atlas = idatlas.readlines()

rutaPlantillaMeta = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Images/Prueba metas/plantillaMeta.txt'
idplantillaMeta = open(rutaPlantillaMeta, 'r')
contenidoDestino = idplantillaMeta.readlines()


rutaPlantilla = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Images/Prueba metas/plantilla.txt'
idplantilla = open(rutaPlantilla, 'r')
plantilla = idplantilla.readlines()

length = len(atlas) 
i = 0


#  lo del id y 213: en la linea 4
#  spritesheed en la linea 98
while i < length:
    contenidoDestinoAux = contenidoDestino.copy()
    i += 1
    # leemos el nombre de la imagen y creamos el nombre del con el .meta
    indexFilename = i
    textureName = atlas[i]
    fileName = textureName.rstrip() + ".meta"
    # nos saltamos los 5 campos siguientes al nombre
    i += 5

    print("file name: ", fileName)
    print("texture name: ", textureName)

    rutaDestino = 'C:/Users/gonza/Documents/UCM/Quinto/Moviles/Practicas/Practica 2/MazesAndMore/Assets/Images/' + fileName
    meta = open(rutaDestino, 'w')

    endPng = True
    count = 0

    #print("-------------------primer sprite: ", atlas[i])

    nLineasTotal = 0
    while endPng: 
        # inserto lo de 213 y secod: (nombre del sprite) -> 4 primeras lineas del documento + las 3N ya escritas + 1/2 lineas escritas 
        contenidoDestinoAux.insert(4 + 3*count, plantilla[0])
        nLineasTotal += 1
        contenidoDestinoAux.insert(4 + 3*count + 1, plantilla[1])
        nLineasTotal += 1
        contenidoDestinoAux.insert(4 + 3*count + 2, plantilla[2].rstrip() + " " + atlas[i])
        nLineasTotal += 1

        nLineasEscritasEncima = 3*(count+1)

        # escribimos la parte de serializedVersion (saltamos a la 6 porque nos saltamos 3 lineas)
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[6])
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        # escribimos el nombre y las otras dos lineas
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[7].rstrip() + " " + atlas[i])
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[8])
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[9])
        nLineasTotal += 1
        nLineasEscritasEncima += 1

        # ----------- WH -----------
        # cojo los valores (i+2+q i=linea del nombre + 2=doslineas mas abajo + q=los mismos calculos para la xy y wh)(le quito xy: y los espacios)
        valores = atlas[i+2+1][atlas[i+2+1].find(':') + 1:]
        index = valores.find(",") + 1
        # valores= " x, y"
        w = valores[1: index-1]
        h = valores[index+1:]

        # inserto los valores 
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[10 + 1*2].rstrip() + " " + w.rstrip() + "\n")
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[11 + 1*2].rstrip() + " " + h.rstrip() + "\n")
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        # ----------- WH -----------

        # ----------- XY -----------
        # cojo los valores (i+2+q i=linea del nombre + 2=doslineas mas abajo + q=los mismos calculos para la xy y wh)(le quito xy: y los espacios)
        valores = atlas[i+2][atlas[i+2].find(':') + 1:]
        index = valores.find(",") + 1
        # valores= " x, y"
        x = valores[1: index-1]
        y = valores[index+1:]

        # sacamos la dimension del png para cambiar el origen de coordenadas
        dimensionPNG = atlas[indexFilename + 1][atlas[indexFilename + 1].find(':') + 1:]
        index2 = dimensionPNG.find(",")
        # sacamos el valor de la y
        totalSizeY = dimensionPNG[index2+1:]
        INTTotalY = int(totalSizeY.rstrip())

        INTY = int(y.rstrip())

        INTY = INTTotalY - INTY - int(h.rstrip())
        print("Y meta: " + str(INTY) + " Y atlas: " + y + " ")
        y = str(INTY)

        # inserto los valores 
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[10].rstrip() + " " + x.rstrip() + "\n")
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[11].rstrip() + " " + y.rstrip() + "\n")
        nLineasTotal += 1
        nLineasEscritasEncima += 1
        # ----------- XY -----------

        w = 0
        # escribimos el resto de lineas
        while(w < 13):
            contenidoDestinoAux.insert(110+nLineasEscritasEncima + 21*count, plantilla[14+w])
            nLineasTotal += 1
            nLineasEscritasEncima += 1
            w += 1

        i += 7
        count += 1
        if(i >= len(atlas) or atlas[i] == '\n'):
            endPng = False

        
    e = 0
    # escribimos las lineas de spritesheed, serializedVersion y sprites
    while(e < 3):
        # count*3=numero de sprites escritos * 3 lineas del principio | 3+e= tercera linea + e lineas  
        contenidoDestinoAux.insert(110+count*3 + e, plantilla[3+e])
        nLineasTotal += 1
        e += 1

    r = 0
    # escribimos las ultimas 10 lineas
    while(r < 10):
        contenidoDestinoAux.insert(110+nLineasTotal, plantilla[27+r])
        nLineasTotal += 1
        r += 1
    
    for item in contenidoDestinoAux:
        meta.write(item)
    
    meta.close

idatlas.close()
