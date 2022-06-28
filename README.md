# BDMCore
The core libraries for the Behaviour-Driven Modelling tool

## Extending the Core library:
    1. Changes made at the lowest level of the library the BDMCore:
      1.a. This could be the preCheck function.
    2. After a ready commitment, to the BDMCore, the developer runs the mvn install command.
      2.a. This command installs the updated version into a local maven repository. 
    3. The version of BDMCore is updated and runs the mvn assembly:single command.
      4.a. This builds a compiled package. 
    5. (currently being implemented) adds this release package to the VS Code extention.
