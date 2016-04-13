package beans;

import java.util.ArrayList;

/**
 * Created by devrath.rathee on 3/13/2016.
 */
public class ServiceBean {

    ArrayList<Service_Categories> service_categories;
    ArrayList<Services> services;

    public ArrayList<Service_Categories> getService_categories() {
        return service_categories;
    }

    public void setService_categories(ArrayList<Service_Categories> service_categories) {
        this.service_categories = service_categories;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }

    public static class Service_Categories{
        String id,name,is_subcategory;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIs_subcategory() {
            return is_subcategory;
        }

        public void setIs_subcategory(String is_subcategory) {
            this.is_subcategory = is_subcategory;
        }
    }

    public static class Services{
        String id,title,desc,service_category_id,min_price,max_price,default_comment1,default_comment2,image_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getService_category_id() {
            return service_category_id;
        }

        public void setService_category_id(String service_category_id) {
            this.service_category_id = service_category_id;
        }

        public String getMin_price() {
            return min_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public String getMax_price() {
            return max_price;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public String getDefault_comment1() {
            return default_comment1;
        }

        public void setDefault_comment1(String default_comment1) {
            this.default_comment1 = default_comment1;
        }

        public String getDefault_comment2() {
            return default_comment2;
        }

        public void setDefault_comment2(String default_comment2) {
            this.default_comment2 = default_comment2;
        }

        public String getImage_path() {
            return image_path;
        }

        public void setImage_path(String image_path) {
            this.image_path = image_path;
        }
    }
}
