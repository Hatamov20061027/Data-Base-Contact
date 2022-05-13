package com.example.contact6

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.contact6.DB.MyDbHelper
import com.example.contact6.Models.MyContact
import com.example.contact_1.Adapters.ContactAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_dialog.view.*

class MainActivity : AppCompatActivity() {
    lateinit var contactAdapter: ContactAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<MyContact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDbHelper = MyDbHelper(this)

        list = myDbHelper.getAllContact()
        contactAdapter = ContactAdapter(list, object : ContactAdapter.OnMyItemListener {
            override fun onClickMyItem(contact: MyContact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(this@MainActivity, imageView)
                popupMenu.inflate(R.menu.popup_manu)

                popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        when (item?.itemId) {
                            R.id.edit -> {
                                val dialog = AlertDialog.Builder(this@MainActivity)
                                var view = LayoutInflater.from(this@MainActivity)
                                    .inflate(R.layout.item_dialog, null)
                                dialog.setView(view)
                                view.add_name1.setText(contact.name)
                                view.add_number2.setText(contact.number)

                                dialog.setPositiveButton(
                                    "Edit",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {
                                            contact.name = view.add_name1.text.toString()
                                            contact.number = view.add_number2.text.toString()
                                            myDbHelper.EditContact(contact)
                                            list[position] = contact
                                            contactAdapter.notifyItemChanged(position)
                                        }
                                    })

                                dialog.show()
                            }
                            R.id.delete -> {
                                myDbHelper.deleteContact(contact)
                                list.remove(contact)
                                contactAdapter.notifyItemRemoved(list.size)
                                contactAdapter.notifyItemRangeChanged(position, list.size)
                            }
                        }
                        return true
                    }
                })

                popupMenu.show()
            }
        })


        add_contact_activity.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            var view =
                LayoutInflater.from(this@MainActivity).inflate(R.layout.item_dialog, null)
            dialog.setView(view)

            dialog.setPositiveButton("Save", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val name = view.add_name1.text.toString()
                    val number = view.add_number2.text.toString()
                    val contact = MyContact(name, number)
                    myDbHelper.addContact(contact)
                    list.add(contact)
                    contactAdapter.notifyItemInserted(list.size)
                }
            })

            dialog.show()
        }
        val simpleCallback =  object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                var position1 = viewHolder.adapterPosition


                val number = list[position1].number

                        val intent = Intent(Intent.ACTION_CALL)
                        intent.data = Uri.parse("tel:$number")
                        if (ActivityCompat.checkSelfPermission(this@MainActivity,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                            return
                        }
                        startActivity(intent)
                       contactAdapter.notifyDataSetChanged()

                    }


        }
        var itemTouchHelper = ItemTouchHelper(simpleCallback)
        var rv1 = findViewById<RecyclerView>(R.id.rv)
        itemTouchHelper.attachToRecyclerView(rv1)
        rv.adapter = contactAdapter
    }
    override fun onResume() {
        contactAdapter.notifyDataSetChanged()
        super.onResume()
    }
}