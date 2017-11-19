package com.example.min.myshopping_list.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.min.myshopping_list.R;
import com.example.min.myshopping_list.data.DAO.ItemDao;
import com.example.min.myshopping_list.data.DAO.ShoppingListDao;
import com.example.min.myshopping_list.data.Database.DBhelper;

import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemActivityFragment extends Fragment {

    public static final String ARG_LIST_ID = "list_Id";
    private int mListId;

    private RecyclerView mRecyclerView;
    private ItemActivityFragment.ItemAdapter mItemAdapter;
    private List<ItemDao> mItemDaos;
            
    private static final int ITEM_NAME = 1;

    public ItemActivityFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == ITEM_NAME &&  resultCode == 0){
            String userInput= (String) data.getSerializableExtra(TextBoxDialogFragment.ARG_INPUT);
            ItemDao itemdao = new ItemDao();
            itemdao.setName(userInput);
            itemdao.setStoreName("");
            itemdao.setNoteText("");
            itemdao.setCrossOff(false);
            itemdao.setListId(mListId);

            DBhelper dBhelper = new DBhelper(getContext());
            dBhelper.addItem(itemdao);
            updateUI();
        }
    }

//    public static ItemActivityFragment newInstance(int listId) {
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_LIST_ID, listId);
//        ItemActivityFragment fragment = new ItemActivityFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListId = ((ItemActivity)getActivity()).mListId;

        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mRecyclerView = view.findViewById(R.id.Item_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  dBhelper = new DBhelper(getContext());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTextBoxDialog("Item Name", "", ITEM_NAME);

            }
        });



        return view;
    }

    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {

        // if (mQuestionDTOs == null)
        DBhelper dBhelper = new DBhelper(getContext());
        mItemDaos = dBhelper.getItems(mListId);

        if (mItemAdapter==null) {
            mItemAdapter = new ItemActivityFragment.ItemAdapter(mItemDaos, getActivity());
            mRecyclerView.setAdapter(mItemAdapter);
        }else{
            mItemAdapter.setShoppingListDTOs(mItemDaos);
            mItemAdapter.notifyDataSetChanged();
        }

        dBhelper.close();
    }

    private void showTextBoxDialog(String title, String orgText, int requestCode){
        DialogFragment newFragment = TextBoxDialogFragment.newInstance(title, orgText, requestCode);
        newFragment.setTargetFragment(this, requestCode);
        newFragment.show(getActivity().getSupportFragmentManager(), title);
    }

    //Innclass Adapter
    private class ItemAdapter extends RecyclerView.Adapter<ItemActivityFragment.ItemsViewHolder>  {

        private List<ItemDao> itemDaos;
        private Context mContext;

        public ItemAdapter(List<ItemDao> listDaos, Context context) {
            itemDaos = listDaos;
            mContext =context;
        }

        @Override
        public ItemActivityFragment.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.item_view_item, parent, false);
            return new ItemActivityFragment.ItemsViewHolder(view, mContext);
        }

        @Override
        public void onBindViewHolder(ItemActivityFragment.ItemsViewHolder holder, int position) {
            ItemDao listDao = itemDaos.get(position);
            holder.bindItem(listDao);
        }

        @Override
        public int getItemCount() {
            return itemDaos.size();
        }

        public void setShoppingListDTOs(List<ItemDao> shoppingListDaos) {
            itemDaos = shoppingListDaos;
        }
    }

    //Innclass ViewHolder
    private class ItemsViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView itemName;
        private EditText storeName;
        private EditText note;
        private CheckBox crossOff;

        private Context mContext;
        private ItemDao itemDao;
        private ImageButton moreButton;



        public ItemsViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = context;

            itemName = (TextView) itemView.findViewById(R.id.itemName);
            storeName = (EditText) itemView.findViewById(R.id.storeName);
            note = (EditText) itemView.findViewById(R.id.note);
            crossOff = (CheckBox) itemView.findViewById(R.id.crossoff);

            storeName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    DBhelper db = new DBhelper(mContext);
                    itemDao.setStoreName(editable.toString());
                    db.updateItem(itemDao);
                }
            });

            note.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    DBhelper db = new DBhelper(mContext);
                    itemDao.setNoteText(editable.toString());
                    db.updateItem(itemDao);
                }
            });



            //moreButton = (ImageButton) itemView.findViewById(R.id.list_more);

            /*
            moreButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater A = LayoutInflater.from(mContext);
                    View moreView = A.inflate(R.layout.moredialog,null);
                    AlertDialog.Builder alerttDialogBuider = new AlertDialog.Builder(mContext);
                    alerttDialogBuider.setView(moreView);
                    //create show up cancel button
                    alerttDialogBuider
                            .setCancelable(true)
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener(){
                                        public  void onClick(DialogInterface dolog, int id) {
                                            dolog.cancel();
                                        }
                                    });


                    final AlertDialog alertDialog = alerttDialogBuider.create();
                    alertDialog.show();

                    Button delete = (Button) moreView.findViewById(R.id.button_delete);

                    delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            android.app.AlertDialog.Builder aBuilder = new android.app.AlertDialog.Builder(mContext);
                            String Alertmessage="You are about to delete this shoppinglist, continue?";

                            aBuilder.setTitle("Alert")
                                    .setMessage(Alertmessage)
                                    .setCancelable(true)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DBhelper DB = new DBhelper(mContext);
                                            DB.removeShoppingList(itemDao.getId());
                                            dialog.cancel();
                                            updateUI();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                            ;
                            android.app.AlertDialog ad = aBuilder.create();
                            ad.show();
                            alertDialog.cancel();
                        }
                    });


                }
            });
            */

        }

        public void bindItem(ItemDao itemDaos) {
            this.itemDao = itemDaos;
            storeName.setText(this.itemDao.getStoreName());
            itemName.setText(this.itemDao.getName());
            note.setText(this.itemDao.getNoteText());
            crossOff.setChecked(this.itemDao.isCrossOff());
        }

        @Override
        public void onClick(View v) {

           // Intent intent = ItemActivity.newIntent(mContext, itemDao.getId());
           // mContext.startActivity(intent);
        }
    }


}

