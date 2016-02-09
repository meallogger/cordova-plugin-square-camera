# Cordova Square Camera Plugin

Wraps android SquareCamera library (https://github.com/boxme/SquareCamera) as cordova plugin.

## Usage

    var opts = { pictureMinWidth: 480, storageDirectory: "MyApp" }
    squareCamera.getPicture(function(pictureUrl) { ... }, function(err) { ... }, opts)


