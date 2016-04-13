package Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.Constants;
import Utils.Utilities;
import beans.SelectedInspection;
import beans.ServiceBean;
import cherry.android.com.cherry.GeneralInspectionDetailActivity;
import cherry.android.com.cherry.R;

/**
 * Created by devrath.rathee on 3/14/2016.
 */
public class GeneralInspectionsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<ServiceBean.Service_Categories> parentServiceCategoriesList;
    private List<ServiceBean.Services> childServicesList;
    private List<ServiceBean.Services> arrayChildServicesList[];
    Resources resources;

    public static String technicianName = "", recordFound = "0",checkedRecordFound = "0";
    public static ServiceBean.Services selectedService;

    public static Map<String,ServiceBean.Services> checkedServicesMap = new HashMap<>();

    public GeneralInspectionsExpandableListAdapter(Context context, List<ServiceBean.Service_Categories> parentList,
                                                   List<ServiceBean.Services> childList) {
        this.context = context;
        checkedServicesMap.clear();
        resources = context.getResources();

        parentServiceCategoriesList = new ArrayList<>();
        for (int k = 0; k < parentList.size(); k++) {
            if (!(parentList.get(k).getIs_subcategory().equals("0")))
                this.parentServiceCategoriesList.add(parentList.get(k));
        }
        arrayChildServicesList = new ArrayList[parentServiceCategoriesList.size()];

        childServicesList = new ArrayList<>();
        this.childServicesList = childList;

        for (int i = 0; i < parentServiceCategoriesList.size(); i++) {
            arrayChildServicesList[i] = new ArrayList<>();
            for (int j = 0; j < childServicesList.size(); j++) {
                if (parentServiceCategoriesList.get(i).getId().equals(childServicesList.get(j).getService_category_id()))
                    arrayChildServicesList[i].add(childServicesList.get(j));
            }

        }
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {

        return arrayChildServicesList[listPosition].get(expandedListPosition);

    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(final int parentPosition, final int expandedChildPosition,
                             boolean isLastChild, View convertView, final ViewGroup parent) {

        try {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.expandablelistview_child_item, null);

            }

            ServiceBean.Services services = arrayChildServicesList[parentPosition].get(expandedChildPosition);
            String imagePath = services.getImage_path();

            TextView inspectionRowTextView = (TextView) convertView.findViewById(R.id.inspection_TextView_Row);
            inspectionRowTextView.setText(imagePath);

            if (imagePath.contains("-"))
                imagePath = imagePath.replace("-", "_");

            imagePath = imagePath.toLowerCase();

            int resourceId = resources.getIdentifier(imagePath, "drawable", context.getPackageName());
            if (!(resourceId == 0)) {
                Drawable drawable = resources.getDrawable(resourceId);
                ImageView inspectionImageView = (ImageView) convertView.findViewById(R.id.inspectionImageView);
                inspectionImageView.setImageDrawable(drawable);
            }

            final ImageView recordImageView = (ImageView) convertView.findViewById(R.id.recordCircularImageView);

            final ImageView checkedImageView = (ImageView) convertView.findViewById(R.id.chechedCircularImageView);
            checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
            checkedImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    checkedRecordFound = "0";

                    if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null) && Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size() > 0) {
                            for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                                if (arrayChildServicesList[parentPosition].get(expandedChildPosition).getId().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId())) {
                                    checkedRecordFound = "1";
                                    recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_unselected));
                                    checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
                                    Utilities.removeInspection(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                                    checkedServicesMap.put(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(),arrayChildServicesList[parentPosition].get(expandedChildPosition));
                                    break;
                                }
                            }

                        if(checkedRecordFound.equals("0")) {
                            checkedServicesMap.put(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(),arrayChildServicesList[parentPosition].get(expandedChildPosition));
                            checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
                            checkedServicesMap.put(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(),arrayChildServicesList[parentPosition].get(expandedChildPosition));
                        }
                    }
                    else {
                        checkedServicesMap.put(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(),arrayChildServicesList[parentPosition].get(expandedChildPosition));
                        checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
                        checkedServicesMap.put(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(),arrayChildServicesList[parentPosition].get(expandedChildPosition));
                    }
                    }
            });

            for(Map.Entry<String,ServiceBean.Services> entry : checkedServicesMap.entrySet())
            {
                if(entry.getKey().equals(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId()))
                {
                    checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
                    break;
                }
                else
                    checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
            }

            recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_unselected));

            if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
                for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                    if (arrayChildServicesList[parentPosition].get(expandedChildPosition).getId().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId())) {
                        recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_selected));
                        checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
                        checkedServicesMap.remove(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                        break;
                    }

                }
            }

            /*if (!foundRecord)
                checkedCircularImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
*/

            recordImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recordFound = "0";

                    if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
                        if (Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size() > 0) {
                            for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                                if (arrayChildServicesList[parentPosition].get(expandedChildPosition).getId().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId())) {
                                    recordFound = "1";
                                    recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_unselected));
                                 //   checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_selected));
                                    Utilities.removeInspection(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                                    break;
                                }
                            }
                        } else {
                            recordFound = "2";
                            Utilities.saveInspection(new SelectedInspection("", "", arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(), "", ""));
                            recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_selected));
                            checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
                            checkedServicesMap.remove(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                        }

                        if (recordFound.equals("0")) {
                            Utilities.saveInspection(new SelectedInspection("", "", arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(), "", ""));
                            recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_selected));
                            checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
                            checkedServicesMap.remove(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                        }
                    } else {
                        Utilities.saveInspection(new SelectedInspection("", "", arrayChildServicesList[parentPosition].get(expandedChildPosition).getId(), "", ""));
                        recordImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.record_selected));
                        checkedImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.checked_unselected));
                        checkedServicesMap.remove(arrayChildServicesList[parentPosition].get(expandedChildPosition).getId());
                    }

                }
            });

            ImageView menuImageView = (ImageView) convertView.findViewById(R.id.menuCircularImageView);
            menuImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* if (!(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null)) {
                        if (Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size() > 0) {
                            for (int i = 0; i < Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).size(); i++) {
                                if (arrayChildServicesList[parentPosition].get(expandedChildPosition).getId().equals(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN).get(i).getId())) {
                    */               selectedService = arrayChildServicesList[parentPosition].get(expandedChildPosition);
                                    technicianName = parentServiceCategoriesList.get(parentPosition).getName();
                                    context.startActivity(new Intent(context, GeneralInspectionDetailActivity.class));

                                  /*  break;
                                }
                            }
                        }
                    }*/

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return arrayChildServicesList[listPosition].size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.parentServiceCategoriesList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return parentServiceCategoriesList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandablelistview_group_item, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.groupItemTextView);
        listTitleTextView.setText(parentServiceCategoriesList.get(listPosition).getName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
