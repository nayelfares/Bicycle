package com.emarketing.bicycle.vm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emarketing.bicycle.R
import com.emarketing.bicycle.api.MainAPIManager
import com.emarketing.bicycle.data.Event
import com.emarketing.bicycle.data.RequestInterface
import com.emarketing.bicycle.data.Response
import com.emarketing.bicycle.mvvm.BaseActivity
import com.emarketing.bicycle.ui.CreateEvent
import com.emarketing.bicycle.ui.EditMaterial
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.event_row.view.*


class EventAdapter(val context:Context, val materials:ArrayList<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.event_row, parent, false))
        return view
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = materials[position]
        with(holder){
            name.text=event.name
            description.text=event.description
            members.text=event.number_members
            startDate.text=event.start_date
            endDate.text =event.end_date
            objectives.text = event.objectives
            if (event.user_id.toInt()==BaseActivity.id){
                edit.visibility=View.VISIBLE
                delete.visibility=View.VISIBLE
            }
            else{
                edit.visibility=View.GONE
                if (BaseActivity.isAdmin)
                    delete.visibility=View.VISIBLE
                else
                    delete.visibility=View.GONE
            }
            edit.setOnClickListener {
                val intent = Intent(context, CreateEvent::class.java)
                intent.putExtra("event", event)
                (context as Activity).startActivityForResult(intent, 1002)
            }
            delete.setOnClickListener {
                (context as BaseActivity).loading()
                delete(event.id.toString(),holder,position)
            }
            if (event.is_joined){
                join.setText(R.string.un_join)
                join.setOnClickListener {
                    (context as BaseActivity).loading()
                    join(false,event.id.toString(),holder,position)
                }
            }else{
                join.setText(R.string.join)
                join.setOnClickListener {
                    (context as BaseActivity).loading()
                    join(true,event.id.toString(),holder,position)
                }
            }

        }

    }

    // total number of rows
    override fun getItemCount(): Int {
        return materials.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name=itemView.name
        val description=itemView.description
        val members =itemView.members
        val startDate =itemView.startDate
        val endDate =itemView.endDate
        val edit=itemView.edit
        val delete =itemView.delete
        val join =itemView.join
        val objectives =itemView.objectives
    }
    fun delete(eventId:String,holder: ViewHolder,position: Int){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.deleteEvent(BaseActivity.token,eventId)
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                    if (t.success){
                        (context as BaseActivity).stopLoading()
                        (context as BaseActivity).showMessage(t.message)
                        materials.removeAt(position)
                        notifyItemRemoved(position)
                        notifyDataSetChanged()
                    }
                }
                override fun onError(e: Throwable) {
                    (context as BaseActivity).stopLoading()
                    (context as BaseActivity).showMessage(e.message.toString())
                }
            })
    }
    fun join(join_unjoin:Boolean,eventId:String,holder: ViewHolder,position: Int){
        val apiManager= MainAPIManager().provideRetrofitInterface().create(RequestInterface::class.java)
        val registerVar  = apiManager.joinEvent(BaseActivity.token,eventId,join_unjoin,BaseActivity.id.toString())
        registerVar.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response> {
                override fun onComplete() { }
                override fun onSubscribe(d: Disposable) { }
                override fun onNext(t: Response) {
                  //  if (t.success){
                        (context as BaseActivity).stopLoading()
                       // (context as BaseActivity).showMessage(t.message)
                        materials[position].is_joined=join_unjoin
                        notifyItemChanged(position)
                        notifyDataSetChanged()
                 //   }
                }
                override fun onError(e: Throwable) {
                    (context as BaseActivity).stopLoading()
                    (context as BaseActivity).showMessage(e.message.toString())
                }
            })
    }
}