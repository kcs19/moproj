package com.example.teamproj

import android.graphics.Paint
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproj.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var array: ArrayList<checkData> = ArrayList<checkData>()
    lateinit var adapter:listAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        binding.recycler.layoutManager= LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter=listAdapter(array)

        adapter.onCheck=object:listAdapter.CheckBoxChangeListener{
            override fun onCheckBoxChanged(
                data: checkData, pos: Int, holder: listAdapter.viewHolder
            ) {
                data.check = holder.binding.checkimage.isChecked
                adapter.notifyDataSetChanged()
            }
        }
        binding.recycler.adapter=adapter

        adapter.onChange=object:listAdapter.AdapterListener{
            override fun onValueReturned(data: checkData,pos:Int,value: String) {
                binding.textcheck.setText(value)
                adapter.removeItem(pos)
            }
        }

        binding.addButton.setOnClickListener {//체크리스트 추가하기
            if(binding.textcheck.length()>=1){
                array.add(checkData(binding.textcheck.text.toString(),false))
                adapter.notifyDataSetChanged()
                binding.textcheck.setText("")
            }
            else Toast.makeText(this, "한글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
        }
        binding.alldelete.setOnClickListener {//체크리스트 모두 지우기
            //val count:Int=adapter.itemCount
            adapter.deleteItem()
        }


    }

}