

xx.ts

launch.json add cfg


 {
            "name": "ts-node ttt ",
            "type": "node",
            "request": "launch",
            "args": [
                "${relativeFile}"
            ],
            "runtimeArgs": [
                "-r",
                "ts-node/register"
            ],
            "cwd": "${workspaceRoot}",
            "protocol": "inspector",
            "internalConsoleOptions": "openOnSessionStart"
        },


# run 
C:\Program Files\nodejs\node.exe -r ts-node/register src\ztest\listMusic.ts