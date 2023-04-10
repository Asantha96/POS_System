package lk.ise.posre.dao.custom.impl;

import lk.ise.posre.dao.CrudUtil;
import lk.ise.posre.dao.custom.ItemDao;
import lk.ise.posre.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDao {
    @Override
    public boolean save(Item item) throws Exception {
        return CrudUtil.execute("INSERT INTO item VALUES(?,?,?,?)",item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
    }

    @Override
    public boolean update(Item item) throws Exception {
        return CrudUtil.execute("UPDATE item SET description=?,unitPrice=?,qtyOnHand=? WHERE code=?",item.getDescription(),item.getUnitPrice(),item.getQtyOnHand(),item.getCode());
    }

    @Override
    public Item find(String code) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item WHERE code=?", code);
        if (resultSet.next()){
            return new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4));

        }else {
            return null;
        }
    }

    @Override
    public boolean delete(String code) throws Exception {
        return CrudUtil.execute("DELETE FROM item WHERE code=?",code);
    }

    @Override
    public List<Item> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM item");
        List<Item> itemList = new ArrayList<>();
        while (resultSet.next()){
            itemList.add(new Item(resultSet.getString(1),resultSet.getString(2),resultSet.getDouble(3),resultSet.getInt(4)));
        }
        return itemList;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT code FROM item");
        List<String>   list = new ArrayList<>();
        while (set.next()){
            list.add(set.getString(1));
        }
        return list;
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item set qty_on_hand=(qty_on_hand-?) WHERE code=?",qty,code);
    }

}
