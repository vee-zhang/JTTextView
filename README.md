# JTTextView

 [ ![Download](https://api.bintray.com/packages/william198824/maven/JTTextView/images/download.svg) ](https://bintray.com/william198824/maven/JTTextView/_latestVersion)  [![License](https://img.shields.io/badge/License-Apache--2.0%20-blue.svg)](./LICENSE)

JTTextView是一个Android的EditText扩展，集成了较多的功能，简单易用，免去了开发很多的工作量。

![效果预览]("https://github.com/william198824/JTTextView/blob/master/play.gif")

## 集成

```groovy
implementation 'com.william:JTTextView:1.0.0'
```

## 使用

### in layout

```xml
<com.william.jttextview.JTTextView
        android:id="@+id/jt_text_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:drawablePadding="10dp"
        android:drawableLeft="@drawable/none"
        app:drawableStart_action="@drawable/yes"
        android:drawableTop="@drawable/none"
        app:drawableTop_action="@drawable/yes"
        android:drawableRight="@drawable/none"
        app:drawableEnd_action="@drawable/yes"
        android:drawableBottom="@drawable/none"
        app:drawableBottom_action="@drawable/yes"
        android:text="single click" />
```

### In kotlin

```kotlin
jt1.setOnDrawableClickListener { switchState, view, actionId, currentText ->

            val str = when (actionId) {
                JTTextView.ACTION_START -> "left drawable"
                JTTextView.ACTION_TOP -> "top drawable"
                JTTextView.ACTION_END -> "right drawable"
                else -> "bottom drawable"
            }

            val test = if (switchState) "selected $str" else "give up $str"

            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
        }
```

## attr属性一览

|ATTR|TYPE|defaultValue|EFFECT|
|------|---------|---------|---------|
|drawableStart_action|drawable|none|点击时展示的左图标|
|drawableTop_action|drawable|none|点击时展示的上图标|
|drawableEnd_action|drawable|none|点击时展示的右图标|
|drawableBottom_action|drawable|none|点击时展示的下图标|
|autoReset|boolean|false|是否自动重置（手指点击触发，抬起则重置，参考demo密码明文显示）|
|autoDisplay|boolean|false|失去焦点时自动隐藏|
|underLine|boolean|false|是否显示文字下划线（颜色随文字颜色）|
|bottomLine|boolean|false|是否显示文字托盘，false则其他托盘设置均失效|
|bottomLineColor|color||托盘颜色|
|bottomLineFocusColor|color|bottomLineColor|托盘聚焦颜色|
|bottomLineStroke|float||托盘线条粗细|
|bottomLineFocusStroke|color|bottomLineStroke|托盘聚焦线条粗细|
|bottomLineStyle|color|line/wall|托盘样式，line:直线   wall:城墙（两端出头）|

## License

    Copyright 2018 william Inc.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.