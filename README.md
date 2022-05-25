# BDMCore
The core libraries for the Behaviour-Driven Modelling tool

## Extending the Core library:

    1. Changes made at the lowest level of the library the BDMLib:
      1.a. This could be the preCheck function.
    2. After a ready commitment, to the BDMLib, the developer runs the mvn install command.
      2.a. This command installs the updated version into a local maven repository. 
    3. The version of BDM lib is updated in the BDM Annotation package. 
    4. The developer updates the package version based on the BDM lib version and runs the mvn assembly:single command.
      4.a. This builds a compiled package with all lower dependencies. 
    5. (currently being implemented) Upload this version to the VS Code extention
