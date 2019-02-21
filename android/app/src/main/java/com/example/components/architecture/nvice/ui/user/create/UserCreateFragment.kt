package com.example.components.architecture.nvice.ui.user.create


import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.ArrayAdapter
import com.example.components.architecture.nvice.BaseFragment

import com.example.components.architecture.nvice.R
import com.example.components.architecture.nvice.databinding.FragmentUserCreateBinding
import com.example.components.architecture.nvice.model.UserPosition
import com.example.components.architecture.nvice.model.UserStatus
import com.example.components.architecture.nvice.ui.camera.CameraActivity
import kotlinx.android.synthetic.main.fragment_user_create.*
import java.util.*
import javax.inject.Inject


class UserCreateFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: UserCreateViewModel
    private lateinit var datePicker: DatePickerDialog

    companion object {
        fun getInstance() = UserCreateFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UserCreateViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        val binding: FragmentUserCreateBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_create, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initDatePicker()
        initObservers()
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_user_create, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val mRandomUser = menu?.findItem(R.id.action_random)
        mRandomUser?.setOnMenuItemClickListener {
            viewModel.randomUser()
            true
        }

        val mScan = menu?.findItem(R.id.action_scan)
        mScan?.setOnMenuItemClickListener {
            activity?.startActivity(Intent(context, CameraActivity::class.java))
            true
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.disposeServices()
    }

    private fun initToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar as Toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(context!!, R.drawable.ic_arrow_back_white_24dp)
        toolbar.title = "Add Staff"
        toolbar.setNavigationOnClickListener { (activity as AppCompatActivity).onBackPressed() }
    }

    private fun initDatePicker() {
        val now = Calendar.getInstance()
        datePicker = DatePickerDialog(context!!,
                DatePickerDialog.OnDateSetListener { _, y, m, d ->
                    viewModel.setDateOfBirth(d, m, y)
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        now.add(Calendar.YEAR, -5)
        datePicker.datePicker.maxDate = now.timeInMillis
        now.add(Calendar.YEAR, -50)
        datePicker.datePicker.minDate = now.timeInMillis
    }

    private fun initObservers() {

        viewModel.userCreateStatus.observe(this, Observer { list ->
            list?.let { status ->
                if (status == UserCreateViewModel.LoadingStatus.FINISHED) {
                    activity?.finish()
                }
            }
        })

        viewModel.showDatePicker.observe(this, Observer { request ->
            request?.let { isDatePickerRequested ->
                if (isDatePickerRequested) {
                    datePicker.show()
                }
            }
        })
    }

    private fun initView() {
        spPosition.getSpinner().adapter = ArrayAdapter<UserPosition>(context!!, R.layout.item_dropdown_custom_field_spinner, UserPosition.values())
        spStatus.getSpinner().adapter = ArrayAdapter<UserStatus>(context!!, R.layout.item_dropdown_custom_field_spinner, UserStatus.values())
    }
}
