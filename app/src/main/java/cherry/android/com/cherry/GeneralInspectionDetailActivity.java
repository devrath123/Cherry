package cherry.android.com.cherry;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import Adapters.GeneralInspectionsExpandableListAdapter;
import Utils.Constants;
import Utils.Utilities;
import beans.SelectedInspection;
import beans.ServiceBean;


public class GeneralInspectionDetailActivity extends ActionBarActivity implements View.OnClickListener {

    TextView technicianNameTextView, saveTextView, twoQuantityTextView, threeQuantityTextView, customQuantityTextView, oneDefaultCommentTextView, twoDefaultCommentTextView, customDefaultCommentTextView, headerTextView, saveGeneralInspectionDetailsTextView;
    boolean oneDefaultCommentTextViewSelected, twoDefaultCommentTextViewSelected, customDefaultCommentTextViewSelected, twoQuantityTextViewSelected, threeQuantityTextViewSelected, customQuantityTextViewSelected, chechedCircularImageViewClicked, recordCircularImageViewClicked;
    RelativeLayout inspectionChildView;
    ImageView recordCircularImageView,chechedImageView;
    ImageView menuCircularImageView;
    EditText customQuantityEditText, customCommentEditText;
    String selectedQuantity = "", selectedComment = "";
    SelectedInspection selectedInspection;
    Button cancelButon, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_inspection_detail);

        hideSoftKeyboard();
        initialiseUI();
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void initialiseUI() {

        inspectionChildView = (RelativeLayout) findViewById(R.id.inspectionChildView);

        saveGeneralInspectionDetailsTextView = (TextView) findViewById(R.id.saveGeneralInspectionDetailsTextView);
        saveGeneralInspectionDetailsTextView.setOnClickListener(this);

        customQuantityEditText = (EditText) findViewById(R.id.customQuantityEditText);
        customCommentEditText = (EditText) findViewById(R.id.customCommentEditText);


        menuCircularImageView = (ImageView) inspectionChildView.findViewById(R.id.menuCircularImageView);
        menuCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        menuCircularImageView.setOnClickListener(this);

        chechedImageView = (ImageView) inspectionChildView.findViewById(R.id.chechedCircularImageView);
        chechedImageView.setOnClickListener(this);

        recordCircularImageView = (ImageView) inspectionChildView.findViewById(R.id.recordCircularImageView);
        //recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_selected));
        //recordCircularImageViewClicked = true;
        recordCircularImageView.setOnClickListener(this);


        String imagePath = GeneralInspectionsExpandableListAdapter.selectedService.getImage_path();

        TextView inspectionRowTextView = (TextView) inspectionChildView.findViewById(R.id.inspection_TextView_Row);
        inspectionRowTextView.setText(imagePath);

        if (imagePath.contains("-"))
            imagePath = imagePath.replace("-", "_");

        imagePath = imagePath.toLowerCase();

        int resourceId = getResources().getIdentifier(imagePath, "drawable", getPackageName());
        if (!(resourceId == 0)) {
            Drawable drawable = getResources().getDrawable(resourceId);

            ImageView inspectionImageView = (ImageView) inspectionChildView.findViewById(R.id.inspectionImageView);
            inspectionImageView.setImageDrawable(drawable);
        }


        headerTextView = (TextView) findViewById(R.id.headerTextView);
        headerTextView.setText(CustomerInfoActivity.customerName);

        cancelButon = (Button) findViewById(R.id.cancelButton);
        cancelButon.setOnClickListener(this);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setVisibility(View.GONE);

        technicianNameTextView = (TextView) findViewById(R.id.technicianNameTextView);
        technicianNameTextView.setText(GeneralInspectionsExpandableListAdapter.technicianName);

        saveTextView = (TextView) findViewById(R.id.saveGeneralInspectionDetailsTextView);
        saveTextView.setOnClickListener(this);

        twoQuantityTextView = (TextView) findViewById(R.id.twoQuantityTextView);
        twoQuantityTextView.setOnClickListener(this);

        threeQuantityTextView = (TextView) findViewById(R.id.threeQuantityTextView);
        threeQuantityTextView.setOnClickListener(this);

        customQuantityTextView = (TextView) findViewById(R.id.customQuantityTextView);
        customQuantityTextView.setOnClickListener(this);

        oneDefaultCommentTextView = (TextView) findViewById(R.id.oneDefaultCommentTextView);
        oneDefaultCommentTextView.setText(GeneralInspectionsExpandableListAdapter.selectedService.getDefault_comment1());
        oneDefaultCommentTextView.setOnClickListener(this);

        twoDefaultCommentTextView = (TextView) findViewById(R.id.twoDefaultCommentTextView);
        twoDefaultCommentTextView.setText(GeneralInspectionsExpandableListAdapter.selectedService.getDefault_comment2());
        twoDefaultCommentTextView.setOnClickListener(this);

        customDefaultCommentTextView = (TextView) findViewById(R.id.customCommentTextView);
        customDefaultCommentTextView.setOnClickListener(this);

     //   Log.i("Size : ","" +GeneralInspectionsExpandableListAdapter.checkedServicesMap.size());

        if(!(GeneralInspectionsExpandableListAdapter.checkedServicesMap == null) && GeneralInspectionsExpandableListAdapter.checkedServicesMap.size() >0) {
            for (Map.Entry<String,ServiceBean.Services> service : GeneralInspectionsExpandableListAdapter.checkedServicesMap.entrySet())
            {
                if(GeneralInspectionsExpandableListAdapter.selectedService.getId().equals(service.getValue().getId())) {
                    chechedImageView.setImageDrawable(getResources().getDrawable(R.drawable.checked_selected));
                    chechedCircularImageViewClicked = true;

                    oneDefaultCommentTextView.setEnabled(false);
                    twoDefaultCommentTextView.setEnabled(false);
                    customDefaultCommentTextView.setEnabled(false);
                    threeQuantityTextView.setEnabled(false);
                    twoQuantityTextView.setEnabled(false);
                    customQuantityTextView.setEnabled(false);
                    break;
                }
            }
        }

        if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
            for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {

                if (Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId().equals(GeneralInspectionsExpandableListAdapter.selectedService.getId())) {
                    recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_selected));
                    recordCircularImageViewClicked = true;
                    String quantity = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getQuantity();
                    if (quantity.equals("3")) {
                        threeQuantityTextViewSelected = true;
                        threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_white));
                    } else if (quantity.equals("2")) {
                        twoQuantityTextViewSelected = true;
                        twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_white));
                    } else {
                        customQuantityEditText.setVisibility(View.VISIBLE);
                        customQuantityTextViewSelected = true;

                        if (quantity.equals("")) {
                            customQuantityTextView.setText("");
                            customQuantityEditText.setText("1");
                        } else {
                            customQuantityTextView.setText("");
                            customQuantityEditText.setText(quantity);
                        }

                        customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        customQuantityEditText.setTextColor(getResources().getColor(R.color.app_white));

                    }

                    String comment = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getComment();

                    if(comment.equals(GeneralInspectionsExpandableListAdapter.selectedService.getDefault_comment1()))
                    {
                        oneDefaultCommentTextViewSelected = true;
                        oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_white));

                    }

                    else if(comment.equals(GeneralInspectionsExpandableListAdapter.selectedService.getDefault_comment2()))
                    {
                        twoDefaultCommentTextViewSelected = true;
                        twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                        twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_white));

                    }
                    else
                    {
                        if(!(comment.equals("")))
                        {
                            customCommentEditText.setVisibility(View.VISIBLE);

                            customDefaultCommentTextViewSelected = true;
                            customCommentEditText.setText(comment);

                            customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                            customCommentEditText.setTextColor(getResources().getColor(R.color.app_white));

                        }
                    }
                }
            }
        }
    }


    private boolean getSelectedQuantity() {
        if (twoQuantityTextViewSelected) {
            selectedQuantity = "2";
            return true;
        } else if (threeQuantityTextViewSelected) {
            selectedQuantity = "3";
            return true;
        } else if (customQuantityTextViewSelected) {

            if (customQuantityEditText.getText().toString().trim().length() > 0) {
                selectedQuantity = customQuantityEditText.getText().toString().trim();
                return true;
            } else {
                Toast.makeText(GeneralInspectionDetailActivity.this, "Enter Quantity.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(GeneralInspectionDetailActivity.this, "Select Quantity.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean getSelectedComment() {
        if (twoDefaultCommentTextViewSelected) {
            selectedComment = twoDefaultCommentTextView.getText().toString();
            return true;
        } else if (oneDefaultCommentTextViewSelected) {
            selectedComment = oneDefaultCommentTextView.getText().toString();
            return true;
        } else if (customDefaultCommentTextViewSelected) {

            if (customCommentEditText.getText().toString().trim().length() > 0) {
                selectedComment = customCommentEditText.getText().toString().trim();
                return true;
            } else {
                Toast.makeText(GeneralInspectionDetailActivity.this, "Enter Comment.", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
            Toast.makeText(GeneralInspectionDetailActivity.this, "Select Comment.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.cancelButton:
                finish();
                break;

            case R.id.menuCircularImageView:

                finish();
                break;

            case R.id.saveGeneralInspectionDetailsTextView:

                if(recordCircularImageViewClicked) {
                    if (getSelectedQuantity()) {
                        if (getSelectedComment()) {
                            selectedInspection = new SelectedInspection("", "", GeneralInspectionsExpandableListAdapter.selectedService.getId(), selectedComment, selectedQuantity);
                            Utilities.updateInspection(selectedInspection);
                            finish();
                        }
                    }
                }
                else if(chechedCircularImageViewClicked)
                {
                    if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
                        for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                            if (Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId().equals(GeneralInspectionsExpandableListAdapter.selectedService.getId())) {
                                Utilities.removeInspection(GeneralInspectionsExpandableListAdapter.selectedService.getId());
                                break;
                            }
                        }
                    }


                    finish();
                }


                break;


            case R.id.recordCircularImageView:

                if (recordCircularImageViewClicked) {
                    recordCircularImageViewClicked = false;
                    recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_unselected));
                } else {
                    recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_selected));

                    recordCircularImageViewClicked = true;

                    oneDefaultCommentTextView.setEnabled(true);
                    twoDefaultCommentTextView.setEnabled(true);
                    customDefaultCommentTextView.setEnabled(true);
                    threeQuantityTextView.setEnabled(true);
                    twoQuantityTextView.setEnabled(true);
                    customQuantityTextView.setEnabled(true);

                    chechedImageView.setImageDrawable(getResources().getDrawable(R.drawable.checked_unselected));
                    chechedCircularImageViewClicked = false;

                    GeneralInspectionsExpandableListAdapter.checkedServicesMap.remove(GeneralInspectionsExpandableListAdapter.selectedService);

                }
                break;

            case R.id.chechedCircularImageView:
                if (chechedCircularImageViewClicked) {
                    chechedImageView.setImageDrawable(getResources().getDrawable(R.drawable.checked_unselected));
                    chechedCircularImageViewClicked = false;

                    GeneralInspectionsExpandableListAdapter.checkedServicesMap.remove(GeneralInspectionsExpandableListAdapter.selectedService);

                    recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_selected));
                    recordCircularImageViewClicked = true;

                    oneDefaultCommentTextView.setEnabled(true);
                    twoDefaultCommentTextView.setEnabled(true);
                    customDefaultCommentTextView.setEnabled(true);
                    threeQuantityTextView.setEnabled(true);
                    twoQuantityTextView.setEnabled(true);
                    customQuantityTextView.setEnabled(true);
                } else {
                    chechedImageView.setImageDrawable(getResources().getDrawable(R.drawable.checked_selected));
                    chechedCircularImageViewClicked = true;

                    GeneralInspectionsExpandableListAdapter.checkedServicesMap.put(GeneralInspectionsExpandableListAdapter.selectedService.getId(),GeneralInspectionsExpandableListAdapter.selectedService);

                    recordCircularImageViewClicked = false;
                    recordCircularImageView.setImageDrawable(getResources().getDrawable(R.drawable.record_unselected));

                    oneDefaultCommentTextViewSelected = false;
                    oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                    oneDefaultCommentTextView.setEnabled(false);

                    twoDefaultCommentTextViewSelected = false;
                    twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                    twoDefaultCommentTextView.setEnabled(false);

                    customCommentEditText.setVisibility(View.GONE);
                    customDefaultCommentTextView.setText("Custom Comment");
                    customDefaultCommentTextViewSelected = false;
                    customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                    customDefaultCommentTextView.setEnabled(false);

                    threeQuantityTextViewSelected = false;
                    threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                    threeQuantityTextView.setEnabled(false);

                    twoQuantityTextViewSelected = false;
                    twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                    twoQuantityTextView.setEnabled(false);

                    customQuantityEditText.setVisibility(View.GONE);

                    customQuantityTextView.setText("Custom Quantity");
                    customQuantityTextViewSelected = false;
                    customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                    customQuantityTextView.setEnabled(false);


                            }

                break;

            case R.id.oneDefaultCommentTextView:
                if (oneDefaultCommentTextViewSelected) {
                    oneDefaultCommentTextViewSelected = false;
                    oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    oneDefaultCommentTextViewSelected = true;
                    oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_white));

                    twoDefaultCommentTextViewSelected = false;
                    twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                    customCommentEditText.setVisibility(View.GONE);
                    customDefaultCommentTextView.setText("Custom Comment");
                    customDefaultCommentTextViewSelected = false;
                    customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                }


                break;

            case R.id.twoDefaultCommentTextView:
                if (twoDefaultCommentTextViewSelected) {
                    twoDefaultCommentTextViewSelected = false;
                    twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    twoDefaultCommentTextViewSelected = true;
                    twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_white));

                    oneDefaultCommentTextViewSelected = false;
                    oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                    customCommentEditText.setVisibility(View.GONE);
                    customDefaultCommentTextView.setText("Custom Comment");
                    customDefaultCommentTextViewSelected = false;
                    customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                }
                break;

            case R.id.customCommentTextView:
                if (customDefaultCommentTextViewSelected) {

                    customCommentEditText.setVisibility(View.GONE);
                    customDefaultCommentTextView.setText("Custom Comment");

                    customDefaultCommentTextViewSelected = false;
                    customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {

                    customCommentEditText.setVisibility(View.VISIBLE);
                    customDefaultCommentTextView.setText("");

                    customDefaultCommentTextViewSelected = true;
                    customDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    customDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_white));

                    oneDefaultCommentTextViewSelected = false;
                    oneDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    oneDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                    twoDefaultCommentTextViewSelected = false;
                    twoDefaultCommentTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoDefaultCommentTextView.setTextColor(getResources().getColor(R.color.app_black));

                }
                break;

            case R.id.twoQuantityTextView:
                if (twoQuantityTextViewSelected) {
                    twoQuantityTextViewSelected = false;
                    twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    twoQuantityTextViewSelected = true;
                    twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_white));

                    threeQuantityTextViewSelected = false;
                    threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                    customQuantityEditText.setVisibility(View.GONE);
                    customQuantityTextView.setText("Custom Quantity");
                    customQuantityTextViewSelected = false;
                    customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                }
                break;

            case R.id.threeQuantityTextView:
                if (threeQuantityTextViewSelected) {
                    threeQuantityTextViewSelected = false;
                    threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {
                    threeQuantityTextViewSelected = true;
                    threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_white));

                    twoQuantityTextViewSelected = false;
                    twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                    customQuantityEditText.setVisibility(View.GONE);
                    customQuantityTextView.setText("Custom Quantity");
                    customQuantityTextViewSelected = false;
                    customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                }
                break;

            case R.id.customQuantityTextView:
                if (customQuantityTextViewSelected) {
                    customQuantityEditText.setVisibility(View.GONE);
                    customQuantityTextView.setText("Custom Quantity");
                    customQuantityTextViewSelected = false;
                    customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    customQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));
                } else {


                    customQuantityEditText.setVisibility(View.VISIBLE);
                    customQuantityTextView.setText("");

                    customQuantityTextViewSelected = true;
                    customQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_pink));
                    customQuantityTextView.setTextColor(getResources().getColor(R.color.app_white));

                    twoQuantityTextViewSelected = false;
                    twoQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    twoQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                    threeQuantityTextViewSelected = false;
                    threeQuantityTextView.setBackgroundColor(getResources().getColor(R.color.app_white));
                    threeQuantityTextView.setTextColor(getResources().getColor(R.color.app_black));

                }
                break;
        }

    }
}
