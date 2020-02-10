package com.example.administrator.wanandroid.mine.todo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.administrator.wanandroid.R;
import com.example.administrator.wanandroid.databinding.FragmentDetailBinding;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoDetailFragment extends Fragment implements View.OnClickListener,TodoHelper.TodoListener{

    private FragmentDetailBinding binding;
    private String status;
    private Event event;
    private TodoHelper helper;
    private int checkedId;
    private int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() != null){
            status = getArguments().getString("status");
        }
        helper = new TodoHelper();
        helper.setTodoListener(this);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,container,false);
        binding.detailBt.setOnClickListener(this);
        binding.detailType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                TodoDetailFragment.this.checkedId = checkedId;
            }
        });
        if(status != null && !status.equals("add")){
            setUnEditable();
            id = getArguments().getInt("id");
            event = (Event) getArguments().getSerializable("detail");
        }
        if(event != null){
            setDefaultEvent();
        }
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TodoMessage message) {
        Log.e("EventBus",message.type+"");
        if(message.type == 1){
            setEditable();
        }else if(message.type == -1){
            setUnEditable();
            setDefaultEvent();
        }
    }

    private void setDefaultEvent(){
        binding.detailTime.setText(event.getDate());
        binding.detailTitle.setText(event.getTitle());
        binding.detailContent.setText(event.getContent());
        switch (event.getType()){
            case Type.LIFE:
                binding.detailTypeLife.setChecked(true);
                break;
            case Type.WORK:
                binding.detailTypeWork.setChecked(true);
                break;
            case Type.LEARN:
                binding.detailTypeLearn.setChecked(true);
                break;
            case Type.OTHER:
                binding.detailTypeOther.setChecked(true);
                break;
        }
    }

    private void setEditable(){
        binding.detailBt.setVisibility(View.VISIBLE);
        binding.detailContent.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.detailTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.detailTime.setInputType(InputType.TYPE_CLASS_TEXT);
        for (int i = 0; i < binding.detailType.getChildCount(); i++) {
            binding.detailType.getChildAt(i).setEnabled(true);
        }
    }

    private void setUnEditable(){
        binding.detailBt.setVisibility(View.GONE);
        binding.detailContent.setInputType(InputType.TYPE_NULL);
        binding.detailTitle.setInputType(InputType.TYPE_NULL);
        binding.detailTime.setInputType(InputType.TYPE_NULL);
        for (int i = 0; i < binding.detailType.getChildCount(); i++) {
            binding.detailType.getChildAt(i).setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detail_bt:
                if(binding.detailTitle.getText().toString().equals("") || binding.detailContent.getText().toString().equals("") || binding.detailTime.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请填齐信息！",Toast.LENGTH_SHORT).show();
                }else {

                    if(checkDateFormat(binding.detailTime.getText().toString())){
                        setUnEditable();
                        Event event = new Event();
                        event.setTitle(binding.detailTitle.getText().toString());
                        event.setContent(binding.detailContent.getText().toString());
                        event.setDate(binding.detailTime.getText().toString());
                        switch (checkedId){
                            case R.id.detail_type_life:
                                event.setType(Type.LIFE);
                                break;
                            case R.id.detail_type_work:
                                event.setType(Type.WORK);
                                break;
                            case R.id.detail_type_learn:
                                event.setType(Type.LEARN);
                                break;
                            case R.id.detail_type_other:
                                event.setType(Type.OTHER);
                                break;
                        }
                        if(status.equals("add")){
                            helper.addEvent(event);
                        }else {
                            helper.modifyEvent(event,id);
                        }
                    }


                }
                break;
        }
    }

    @Override
    public void onSuccess(int position, int type) {
        if(type == TodoHelper.ADD){
            Toast.makeText(getContext(),"新增成功!",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }else if(type == TodoHelper.MODIFY){
            Toast.makeText(getContext(),"修改成功!",Toast.LENGTH_SHORT).show();
            TodoMessage message = new TodoMessage();
            message.type = 3;
            EventBus.getDefault().post(message);
        }
    }

    @Override
    public void onFail(String msg, int type) {
        if(type == TodoHelper.ADD){
            Toast.makeText(getContext(),"新增失败!",Toast.LENGTH_SHORT).show();

        }else if(type == TodoHelper.MODIFY){
            Toast.makeText(getContext(),"修改失败!",Toast.LENGTH_SHORT).show();
        }
        setEditable();
    }

    private boolean checkDateFormat(String date){
        String[] strs = date.split("-");
        if(strs.length == 3 && strs[0].length() == 4 && strs[1].length() == 2 && strs[2].length() == 2){
            Date dateNow = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String strNow = df.format(dateNow);
            if(date.equals(strNow)){
                return true;
            }
            try {
                Date now = df.parse(strNow);
                Date set = df.parse(date);
                if(set.getTime() < now.getTime()){
                    Toast.makeText(getContext(),"预计完成日期不能小于当前日期",Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getContext(),"预计完成日期格式错误",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        Toast.makeText(getContext(),"预计完成日期格式错误",Toast.LENGTH_SHORT).show();
        return false;
    }
}
