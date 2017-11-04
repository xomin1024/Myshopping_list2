package com.example.min.myshopping_list.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.example.min.myshopping_list.
import com.example.min.myshopping_list.R;
import com.example.min.myshopping_list.data.DAO.ShoppingListDao;
import com.example.min.myshopping_list.data.Database.DBhelper;

import java.util.Date;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class ShoppingListActivityFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ShoppingListAdapter mSiteVisitAdapter;
    List<ShoppingListDao> mShoppingListDaos;
    private static final int SHOPPING_LIST_NAME=1;

    public ShoppingListActivityFragment() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == SHOPPING_LIST_NAME &&  resultCode == 0){
            String userInput= (String) data.getSerializableExtra(TextBoxDialogFragment.ARG_INPUT);
            ShoppingListDao listDao = new ShoppingListDao();
            listDao.setDate(new Date());
            listDao.setName(userInput);

            DBhelper dBhelper = new DBhelper(getContext());
            dBhelper.addShoppingList(listDao);
            updateUI();
        }




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.fragment_shopping_list, container, false);

        mRecyclerView =  view.findViewById(R.id.shoppingList_recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //  dBhelper = new DBhelper(getContext());

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showTextBoxDialog("Shopplist Name", "", SHOPPING_LIST_NAME);

            }
        });

        return  view;
    }

    public void onResume() {
        super.onResume();
        updateUI();
    }



    public void updateUI() {

        // if (mQuestionDTOs == null)
        DBhelper dBhelper = new DBhelper(getContext());
        mShoppingListDaos = dBhelper.getShoppingLis();

        if (mSiteVisitAdapter==null) {
            mSiteVisitAdapter = new ShoppingListAdapter(mShoppingListDaos, getActivity());
            mRecyclerView.setAdapter(mSiteVisitAdapter);
        }else{
            mSiteVisitAdapter.setShoppingListDTOs(mShoppingListDaos);
            mSiteVisitAdapter.notifyDataSetChanged();
        }

        dBhelper.close();
    }

    private void showTextBoxDialog(String title, String orgText, int requestCode){
        DialogFragment newFragment = TextBoxDialogFragment.newInstance(title, orgText, requestCode);
        newFragment.setTargetFragment(this, requestCode);
        newFragment.show(getActivity().getSupportFragmentManager(), title);
    }

    //Innclass Adapter
    private class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListViewHolder>  {

        private List<ShoppingListDao> shoppingListDaoList;
        private Context mContext;

        public ShoppingListAdapter(List<ShoppingListDao> listDaos, Context context) {
            shoppingListDaoList = listDaos;
            mContext =context;
        }

        @Override
        public ShoppingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.shopping_list_view_item, parent, false);
            return new ShoppingListViewHolder(view, mContext);
        }

        @Override
        public void onBindViewHolder(ShoppingListViewHolder holder, int position) {
            ShoppingListDao listDao = shoppingListDaoList.get(position);
            holder.bindShoppingList(listDao);
        }

        @Override
        public int getItemCount() {
            return shoppingListDaoList.size();
        }

        public void setShoppingListDTOs(List<ShoppingListDao> shoppingListDaos) {
            shoppingListDaoList = shoppingListDaos;
        }
    }

    //Innclass ViewHolder
    private class ShoppingListViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        private TextView listDate;
        private TextView listName;
        private Context mContext;
        private ShoppingListDao shoppingListDao;
        private ImageButton moreButton;



        public ShoppingListViewHolder(View itemView, Context context) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = context;

            listDate = (TextView) itemView.findViewById(R.id.list_date);
            listName = (TextView) itemView.findViewById(R.id.list_name);
            moreButton = (ImageButton) itemView.findViewById(R.id.list_more);

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
                                            DB.removeShoppingList(shoppingListDao.getId());
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

        }

        public void bindShoppingList(ShoppingListDao shoppingListDao) {
            this.shoppingListDao = shoppingListDao;
            listName.setText(this.shoppingListDao.getName());
            listDate.setText(this.shoppingListDao.getDate().toString());
        }

        @Override
        public void onClick(View v) {

            Intent intent = ItemActivity.newIntent(mContext, shoppingListDao.getId());
            mContext.startActivity(intent);
        }
    }
}
