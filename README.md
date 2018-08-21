# JTTextView
JTTextView是一个Android的EditText扩展，集成了较多的功能，简单易用，免去了开发很多的工作量。
![效果预览]("play.gif")

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

|ATTR|TYPE|EFFECT|
|------|---------|---------|
|drawableStart_action|drawable|点击时展示的左图标|
|drawableTop_action|drawable|点击时展示的上图标|
|drawableEnd_action|drawable|点击时展示的右图标|
|drawableBottom_action|drawable|点击时展示的下图标|
