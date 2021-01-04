var fs = require("fs");
var path = require('path');

const dirName = 'traps';

const directoryPath = path.join(__dirname, dirName);
let files = null;

function readFile(fileName) {
    var filePath = "./" + dirName + "/" + fileName;
    fs.readFile(filePath, 'ascii', function (err, content) {
        if (err) {
            console.log(err);
            process.exit(1);
        }
        let text = "";
        for (var i = 0; i < content.length; i++) {
            if (content[i].charCodeAt(0) >= 97 && content[i].charCodeAt(0) <= 122) {
                let aux = '"' + content[i] + '"';
                text += aux;
            }
            else {
                text = text.concat(content[i]);
            }
        }
        fs.writeFile("nuevosNiveles/" + fileName, text, () => { });
    });
}

var numFiles = 0;

fs.readdir(directoryPath, function (err, files) {
    if (err) {
        return console.log('Unable to scan directory: ' + err);
    }
    files.forEach(function (file) {
        files.push(file);
        readFile(file);
        numFiles++;
    });
    console.log(numFiles + " archivos parseados\n");
})