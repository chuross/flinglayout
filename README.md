[![](https://jitpack.io/v/chuross/flinglayout.svg)](https://jitpack.io/#chuross/flinglayout)

# FlingLayout
**Recommend using Kotlin project**

This Layout provide fling dismiss effect like Twitter ImageView.

![](https://user-images.githubusercontent.com/1422031/31901843-b1bdb554-b85d-11e7-9ae4-cc49b3a161b2.gif)

## Usage
see [sample](https://github.com/chuross/flinglayout/blob/master/app/src/main/java/com/github/chuross/flinglayout/sample/MainActivity.java)

This sample use [PhothoView](https://github.com/chrisbanes/PhotoView)

### In your layout
Single View into FlingLayout.

```
<com.github.chuross.flinglayout.FlingLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <!-- something view parent -->

</com.github.chuross.flinglayout.FlingLayout>
```

### Using this layout
```
val flingLayout = findViewById<FlingLayout>(R.id.flinglayout)
flingLayout.dismissListener = { /** something your code **/ }
```

### XMLAttributes
| name | type | description | etc |
| --- | --- | --- | --- |
| fl_isDragEnabled | boolean | | |
| fl_isDismissEnabled | boolean | | |

## Download
### Gradle

1. add JitPack repository to your project root `build.gradle`.

```
repositories {
    maven { url "https://jitpack.io" }
}
```

2. add the dependency
latest version: [![](https://jitpack.io/v/chuross/flinglayout.svg)](https://jitpack.io/#chuross/flinglayout)

```
dependencies {
    compile 'com.github.chuross:flinglayout:x.x.x'
}
```

## License
```
Copyright 2017 chuross

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
