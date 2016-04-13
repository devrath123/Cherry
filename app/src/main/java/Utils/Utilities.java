package Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.HashMap;

import beans.SearchVinBean;
import beans.SelectedInspection;

/**
 * Created by devrath.rathee on 3/13/2016.
 */
public class Utilities {

    static ArrayList<SelectedInspection> selectedInspectionArrayList;
    public static HashMap<String,ArrayList<SelectedInspection>> selectedInspectionsHashMap;
    public static HashMap<String, SearchVinBean.Data> customerInfoHashMap;

    public static RequestQueue getRequestQueue(Context context)
    {
        return VolleySingleton.getInstance(context).getRequestQueue();
    }

    public static void serverError(Context context)
    {
        Toast.makeText(context,"Server Error.Try again later.",Toast.LENGTH_SHORT).show();
    }

    public static void internetConnectionError(Context context)
    {
        Toast.makeText(context,"Check Internet Connectivity.",Toast.LENGTH_SHORT).show();
    }


    /**Customer Info HashMap */
    public static void saveCustomerInfoHashMap(String vin,SearchVinBean.Data searchVinBeanData)
    {
        if(customerInfoHashMap == null)
            customerInfoHashMap = new HashMap<>();

        customerInfoHashMap.put(vin,searchVinBeanData);
    }

    public static SearchVinBean.Data getCustomerInfo(String vin)
    {
        return customerInfoHashMap.get(vin);

    }



     /** SelectedInpectionArrayList handling */
    public static ArrayList<SelectedInspection> getTempArrayList()
    {
        return selectedInspectionArrayList;

    }

    public static void clearTempArrayList()
    {
        selectedInspectionArrayList.clear();
    }




    public static void setSelectedInspectionArrayList(ArrayList<SelectedInspection> selectedInspectionArrayList) {
        Utilities.selectedInspectionArrayList = selectedInspectionArrayList;
    }

    public static void saveInspection(SelectedInspection selectedInspection)
    {
        if(selectedInspectionArrayList == null)
            selectedInspectionArrayList = new ArrayList<>();

        if(selectedInspectionArrayList.size() == 0 && !(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null))
            selectedInspectionArrayList = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN);

        selectedInspectionArrayList.add(selectedInspection);
        saveSelectedInspectionInfoHashMap(Constants.CURRENT_VIN,selectedInspectionArrayList);

    }



    public static void removeInspection(String title,String desc)
    {
        if(selectedInspectionArrayList.size() == 0)
            selectedInspectionArrayList = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN);

        for(int i=0;i<selectedInspectionArrayList.size();i++)
        {
            if(selectedInspectionArrayList.get(i).getDesc().equals(desc) && selectedInspectionArrayList.get(i).getTitle().equals(title))
                selectedInspectionArrayList.remove(i);

            Log.i("Removed Size : ","" + selectedInspectionArrayList.size());

        }

        saveSelectedInspectionInfoHashMap(Constants.CURRENT_VIN,selectedInspectionArrayList);
    }



    public static ArrayList<SelectedInspection> getSelectedInspectionArrayList(String vin) {

        if(selectedInspectionsHashMap == null)
            selectedInspectionsHashMap = new HashMap<>();

        return selectedInspectionsHashMap.get(vin);
    }


    public static void updateInspection(SelectedInspection selectedInspection)
    {
        boolean found = false;

        if(selectedInspectionArrayList == null)
            selectedInspectionArrayList = new ArrayList<>();

        if(selectedInspectionArrayList.size() == 0 && !(Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN) == null))
            selectedInspectionArrayList = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN);

        if(selectedInspectionArrayList.size()> 0) {
            for (int i = 0; i < selectedInspectionArrayList.size(); i++) {
                if (selectedInspectionArrayList.get(i).getId().equals(selectedInspection.getId())) {
                    found = true;
                    selectedInspectionArrayList.get(i).setComment(selectedInspection.getComment());
                    selectedInspectionArrayList.get(i).setQuantity(selectedInspection.getQuantity());
                    break;
                }
            }

            if(!found)
                selectedInspectionArrayList.add(selectedInspection);
        }
        else
        selectedInspectionArrayList.add(selectedInspection);

        saveSelectedInspectionInfoHashMap(Constants.CURRENT_VIN,selectedInspectionArrayList);
    }

    public static void removeInspection(String id)
    {
        if(selectedInspectionArrayList.size() == 0)
            selectedInspectionArrayList = Utilities.getSelectedInspectionArrayList(Constants.CURRENT_VIN);

        for(int i=0;i<selectedInspectionArrayList.size();i++)
        {
            if(selectedInspectionArrayList.get(i).getId().equals(id) ) {
                selectedInspectionArrayList.remove(i);
                break;
            }
        }

        saveSelectedInspectionInfoHashMap(Constants.CURRENT_VIN,selectedInspectionArrayList);
    }


    //ScanVINActivity.searchVinBean.getData().get(0).getVin()

    public static void saveSelectedInspectionInfoHashMap(String vin,ArrayList<SelectedInspection> selectedAL)
    {
        if(selectedInspectionsHashMap == null)
            selectedInspectionsHashMap = new HashMap<>();

        if(!(selectedAL == null) ) {
            ArrayList<SelectedInspection> tempArrayList = new ArrayList<>();
            if(selectedAL.size() > 0) {
               // ArrayList<SelectedInspection> tempArrayList = new ArrayList<>();
                tempArrayList.clear();
                for (SelectedInspection selectedInspection : selectedAL) {
                    tempArrayList.add(selectedInspection);
                }

                selectedInspectionsHashMap.put(vin, tempArrayList);
            }
            else
                selectedInspectionsHashMap.put(vin,tempArrayList);
        }
    }



}
