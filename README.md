# Ricochet

*LibGDX 2D engine with new vector-based physics functions and sprite processing that makes developing easier.*

## Features

- Easy Box2D shape creator in code, sprite wrapper
- Cameras with zoom and translate interpolation built in
- Tiled map loader and manager that can convert layers to collideable objects
- Text renderer that schedule drawing
- Vector tools like dot product, etc.
- Modified shape renderer to draw perfect rounded rectangles

## To Do

- Effect factory/renderer with easy import and timeout
- Fix hitbox definitions
- ASCII room layout cast to object
- Camera actions sequences, follow
- Realistic lighting
- Music manager
- overrideable input handling


## How To Install

### Maven

Add this to your `pom.xml`:

```$xslt
<dependency>
  <groupId>me.rohanbansal.ricochet</groupId>
  <artifactId>ricochet</artifactId>
  <version>1.5-SNAPSHOT</version>
</dependency>
```

### Gradle

Add this to your `build.gradle`:

```$xslt
compile "me.rohanbansal.ricochet:ricochet:1.5-SNAPSHOT"
```
