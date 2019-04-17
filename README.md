# Databinding - Study

- 시작 동기 : MVVM
- 목표 : 2-way Databinding (MVVM에 사용하기 위해서! 많은 게시물에서 2-way까지 아는 것이 좋다고 한다)



- 정의 : **xml에 데이터를 바인딩하여 불필요한 코드를 줄이는 방법**
- 참고 내용
  - [데이터 바인딩 docs](https://developer.android.com/topic/libraries/data-binding)
  - [간단한 Data binding 예시](https://medium.com/@PaperEd/android-how-to-databinding-169c78e7dc28)
  -  [2-way binding 설명](https://blog.yatopark.net/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%9D%98-2-way-%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B0%94%EC%9D%B8%EB%94%A9/), [데이터 바인딩 docs 중 - Two-way data binding](https://developer.android.com/topic/libraries/data-binding/two-way#kotlin)



1. gradle 추가

   ```gradle
   apply plugin: "kotlin-kapt"

   android {
       ....
       dataBinding {
           enabled = true
       }
   }
   ```



2. DataBinding을 위한 **ViewModel**을 만들기

```kotlin
import android.databinding.ObservableField
import android.view.View
import android.widget.Toast

class MainViewModel {
    val text = ObservableField<String>("")

    fun showText(view : View) {
        Toast.makeText(view.context, "${text.get()}", Toast.LENGTH_SHORT).show()
    }
}
```

 - Observable?

   - 데이터 바인딩에서는 ObservableField를 포함한 데이터 바인딩 라이브러리의 Observable들을 이용해서 UI에 데이터에 접근한다.
   - 자바의 원시타입들은 ObservableInt처럼, Observable + 원시타입 이름 으로 접근할수 있다.

   - private 메소드들은 xml에서 참조할 수 없다.



3. xml 참조를 위한 수정.

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <layout xmlns:android="http://schemas.android.com/apk/res/android">
       <data>
           <import type="android.view.View" />
           <variable
               name="vm"
               type="com.example.databinding_study.viewmodel.MainViewModel" />
       </data>

       <LinearLayout>
   	    <TextView
                   android:text="@={vm.text}" />
           <Button
               android:onClick="@{(view) -> vm.showText(view) }" />
       </LinearLayout>
   </layout>
   ```

   - 데이터 바인딩을 위해서, 루트 태그를 `<layout>`으로 바꾸어 주자.
     - 기존 Layout은 루트 태그 아래에 두자
     - data 태그로 ViewModel을 참조하자
   - import : Context. View 등 객체를 사용하기 위해서는 `<data>` 내부에 사용한 객체를 import해주어야 한다. (여기서는 onClick을 사용하기 위해서 View를 가져왔다)

   - variable : variable-name으로 변수명 지정, variable-type으로 그 속성을 가져온다.
     - 이렇게 하면 MainViewModel의 변수들과 메소드에 접근이 가능하다.
   - @{ } : 레이아웃 내에서 MainViewModel에 접근하기 위해서는 @{ } 구문을 이용해 특정 속성을 사용하려고 한다. [사용할수 있는 식들의 속성](https://developer.android.com/topic/libraries/data-binding/?hl=ko#expression_language)

4. Activity에 ViewModel을 바인딩!

   ```kotlin
   val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
   binding.vm = MainViewModel()
   ```

   - Activity에 이 코드를 작성해주자.
   - `setContentView(R.layout.activity_main)`는 데이터바인딩에서 사용되므로 지워도 된다.



5. 구현은 되었는데 그래서 2-way Binding이 무엇인가

   - 네, 방금까지 한거요.
   - EditText에서 사용된 `android:text="@={vm.text}"` 중 @={ }
     - 이 문법이 양방향 바인딩을 시켜주는 문법.
     - [이런것들 양방향 바인딩 쓰는거](https://halfthought.wordpress.com/2016/03/23/2-way-data-binding-on-android/)

   - `@BindingAdapter` Annotation? `@InverseBindingAdatper` Annotation?
     - `@BindingAdapter` : Custom View를 만들었거나 Custom Attributes에서 사용할 때 쓰는 어노테이션.
     - [2-way data binding using custom attributes](https://developer.android.com/topic/libraries/data-binding/two-way#two-way-custom-attrs)
     - `@InverseBindingAdatper` : 그에 따라, CustomView나 Custom Attributes를 위한 양방향 데이터바인딩을 위한 어노테이션.



6. 구현이 되면 뭐해 자주 쓰는 복잡한 View들과의 연계는?
   - Fragment와 Adapter과 MVVM은 [AndroidMVVM-Study](https://github.com/KRMKGOLD/Android_MVVMSample)에서 공부할 예정이다.

