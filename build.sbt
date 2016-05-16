name			:= "jkeyfinder"
organization	:= "de.djini"
version			:= "0.3.0"

javacOptions	++= Seq(
	"-source", "1.8",
	"-target", "1.8"
)

conflictManager		:= ConflictManager.strict
libraryDependencies	+= "com.github.wendykierp"	% "JTransforms"	% "3.1"	% "compile"

// this is a pure java project
crossPaths			:= false
autoScalaLibrary	:= false

EclipseKeys.projectFlavor			:= EclipseProjectFlavor.Java
EclipseKeys.executionEnvironment	:= Some(EclipseExecutionEnvironment.JavaSE18)
EclipseKeys.eclipseOutput			:= Some("target/eclipse-classes")
