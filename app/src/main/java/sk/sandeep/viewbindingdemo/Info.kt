package sk.sandeep.viewbindingdemo

/*
reference :: => https://developer.android.com/topic/libraries/view-binding

View binding is a feature that allows you to more easily write code that interacts with views. Once view binding is enabled in a module, it generates a binding class for each XML layout file present in that module. An instance of a binding class contains direct references to all views that have an ID in the corresponding layout.

In most cases, view binding replaces findViewById.

To enable view binding in a module, set the viewBinding build option to true in the module-level build.gradle

android {
    ...
    buildFeatures {
        viewBinding = true
    }
}


If you want a layout file to be ignored while generating binding classes, add the tools:viewBindingIgnore="true" attribute to the root view of that layout file:

<LinearLayout
        ...
        tools:viewBindingIgnore="true" >
    ...
</LinearLayout>

Every binding class also includes a getRoot() method, providing a direct reference for the root view of the corresponding layout file. In this example, the getRoot() method in the ResultProfileBinding class returns the LinearLayout root view.

Use view binding in activities
To set up an instance of the binding class for use with an activity, perform the following steps in the activity's onCreate() method:

1.Call the static inflate() method included in the generated binding class. This creates an instance of the binding class for the activity to use.
2.Get a reference to the root view by either calling the getRoot() method or using Kotlin property syntax.
3.Pass the root view to setContentView() to make it the active view on the screen.

result_profile.xml

<LinearLayout ... >
    <TextView android:id="@+id/name" />
    <ImageView android:cropToPadding="true" />
    <Button android:id="@+id/button"
        android:background="@drawable/rounded_button" />
</LinearLayout>

ResultProfileActivity {

private lateinit var binding: ResultProfileBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ResultProfileBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.name.text = viewModel.name
    binding.button.setOnClickListener { viewModel.userClicked() }
   }
}


Use view binding in fragments =>

To set up an instance of the binding class for use with a fragment, perform the following steps in the fragment's onCreateView() method:

1.Call the static inflate() method included in the generated binding class. This creates an instance of the binding class for the fragment to use.
2.Get a reference to the root view by either calling the getRoot() method or using Kotlin property syntax.
3.Return the root view from the onCreateView() method to make it the active view on the screen.

Note: The inflate() method requires you to pass in a layout inflater. If the layout has already been inflated, you can instead call the binding class's static bind() method

class BindFragment : Fragment(R.layout.fragment_blank) {

    private var _binding: ResultProfileBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = ResultProfileBinding.inflate(inflater, container, false)
            val view = binding.root
            return view
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

}

class BindFragment : Fragment(R.layout.fragment_blank) {

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var fragmentBlankBinding: FragmentBlankBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentBlankBinding.bind(view)
        fragmentBlankBinding = binding
        binding.textViewFragment.text = getString(string.hello_from_vb_bindfragment)
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        fragmentBlankBinding = null
        super.onDestroyView()
    }
}


Differences from findViewById =>

View binding has important advantages over using findViewById:

1.Null safety: Since view binding creates direct references to views, there's no risk of a null pointer exception due to an invalid view ID. Additionally, when a view is only present in some configurations of a layout, the field containing its reference in the binding class is marked with @Nullable.
2.Type safety: The fields in each binding class have types matching the views they reference in the XML file. This means that there's no risk of a class cast exception.

These differences mean that incompatibilities between your layout and your code will result in your build failing at compile time rather than at runtime.

Comparison with dataBinding=>
View binding and data binding both generate binding classes that you can use to reference views directly. However, view binding is intended to handle simpler use cases and provides the following benefits over data binding:

1.Faster compilation: View binding requires no annotation processing, so compile times are faster.
2.Ease of use: View binding does not require specially-tagged XML layout files, so it is faster to adopt in your apps. Once you enable view binding in a module, it applies to all of that module's layouts automatically.
* */