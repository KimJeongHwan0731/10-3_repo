package com.example.a10_3_dialogue

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.a10_3_dialogue.databinding.ActivityMainBinding
import com.example.a10_3_dialogue.databinding.RegisterLayoutBinding
import com.example.a10_3_dialogue.databinding.ToastLayoutBinding
import java.net.URI

class MainActivity : AppCompatActivity(), View.OnClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnToast.setOnClickListener(this)
        binding.btnDate.setOnClickListener(this)
        binding.btnTime.setOnClickListener(this)
        binding.btnDialog.setOnClickListener(this)
        binding.btnItemDialog.setOnClickListener(this)
        binding.btnMultiItemDialog.setOnClickListener(this)
        binding.btnSingleItemDialog.setOnClickListener(this)
        binding.btnCustomDialog.setOnClickListener(this)
        binding.btnFindLocate.setOnClickListener(this)
        binding.btnRington.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnToast -> {
                val toast = Toast.makeText(applicationContext, "토스트 메세지 보이기", Toast.LENGTH_SHORT)
                toast.duration = Toast.LENGTH_LONG
                toast.setGravity(Gravity.TOP, 0, 200)
                var toastLayoutBinding: ToastLayoutBinding
                toastLayoutBinding = ToastLayoutBinding.inflate(layoutInflater)
                toast.view = toastLayoutBinding.root
//                toast.setText("정신차리자")
//                toast.addCallback(object : Toast.Callback() {
//                    override fun onToastShown() {
//                        super.onToastShown()
//                        binding.btnToast.text = "토스트 보이기"
//                    }
//
//                    override fun onToastHidden() {
//                        super.onToastHidden()
//                        binding.btnToast.text = "토스트 감추기"
//                    }
//                })
//                val toastLayout = LayoutInflater.from(applicationContext).inflate(R.layout.toast_layout,null)
//                val textView = toastLayout.findViewById<TextView>(R.id.textView)
//                textView.setOnClickListener {
//                    binding.btnToast.text = getString(R.string.app_name)
//                }
//                toast.view = toastLayout.rootView
                toast.show()
            }
            R.id.btnDate -> {
                DatePickerDialog(this, this, 2023, 3 - 1, 23).show()
            }
            R.id.btnTime -> {
                TimePickerDialog(this, this, 13, 45, false).show()
            }
            R.id.btnDialog -> {
                val eventHandler = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(
                            this@MainActivity, "${
                                if (which == -1) {
                                    "ok"
                                } else {
                                    "no"
                                }
                            }클릭하셨어요", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setMessage("알림창 정보를 보여드립니다.")
                    setPositiveButton("YES", eventHandler)
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            }
            R.id.btnItemDialog -> {
                val eventHandler = object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(
                            this@MainActivity, "${
                                if (which == -1) {
                                    "ok"
                                } else {
                                    "no"
                                }
                            }클릭하셨어요", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setItems(items, object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("NO", eventHandler)
                    show()
                }
            }
            R.id.btnMultiItemDialog -> {
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setMultiChoiceItems(
                        items,
                        booleanArrayOf(true, false, false, false),
                        object : DialogInterface.OnMultiChoiceClickListener {
                            override fun onClick(
                                dialog: DialogInterface?,
                                which: Int,
                                isChecked: Boolean
                            ) {
                                if (isChecked == true) {
                                    binding.btnMultiItemDialog.text = items[which]
                                }
                            }
                        })
                    setPositiveButton("YES", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            Toast.makeText(applicationContext, "선택했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    })
                    setNegativeButton("Cancel", null)
                    show()
                }
            }
            R.id.btnSingleItemDialog -> {
                val items = arrayOf<String>("홍길동", "저길동", "구길동", "수길동")
                AlertDialog.Builder(this).run {
                    setTitle("알림창")
                    setIcon(R.drawable.computer_24)
                    setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            binding.btnSingleItemDialog.text = items[which]
                        }
                    })
                    setNegativeButton("CLOSE", null)
                    setCancelable(false)
                    show()
                }.setCanceledOnTouchOutside(true)
            }
            R.id.btnCustomDialog -> {
                val userBinding: RegisterLayoutBinding
                val dialogBuilder = AlertDialog.Builder(this)
                var userDialog: AlertDialog

                // 사용자 화면 인플렉션하기
                userBinding = RegisterLayoutBinding.inflate(layoutInflater)
                // 사용자 다이얼로그 제목, 뷰 설정 보이기
                dialogBuilder.setTitle("사용자 이름 입력하기 창")
                dialogBuilder.setIcon(R.drawable.computer_24)
                dialogBuilder.setView(userBinding.root)
                // dialogBuilder.create() dialogBuilder 정보를 dismiss() 새로 추가해서 userDialog 넘겨줌
                userDialog = dialogBuilder.create()
                userDialog.show()
                // 이벤트처리하기
                userBinding.btnCancel.setOnClickListener {
                    Toast.makeText(applicationContext, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                    userDialog.dismiss()
                }
                userBinding.btnRegister.setOnClickListener {
                    binding.tvMessage.text = userBinding.edtName.text.toString()
                    userDialog.dismiss()
                }
            }
            R.id.btnFindLocate -> {
                val state = ContextCompat.checkSelfPermission(applicationContext, "android.permission.ACCESS_FINE_LOCATION")
                if(state == PackageManager.PERMISSION_GRANTED){
                    binding.tvMessage.text = "위치추적권한허용"
                } else{
                    binding.tvMessage.text = "위치추적권한불허"
                }
            }
            R.id.btnRington -> {
                // Uniform Resource Identifier, 리소스를 구분하는 식별자.
                // 안드로이드에서 URI의 역할은 리소스(외부 앱, 이미지, 텍스트 등)에 접근할 수 있는 식별자 역할
                var notificationUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val ringtone = RingtoneManager.getRingtone(applicationContext,notificationUri)
                ringtone.play()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        Toast.makeText(applicationContext, "$year-${month + 1}-$day", Toast.LENGTH_SHORT).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Toast.makeText(applicationContext, "${hourOfDay}시 ${minute}분", Toast.LENGTH_SHORT).show()
    }

}
