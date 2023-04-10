package lk.ise.posre.bo.custom.impl;

import lk.ise.posre.bo.custom.ItemBo;
import lk.ise.posre.dao.custom.ItemDao;
import lk.ise.posre.dao.custom.impl.ItemDaoImpl;
import lk.ise.posre.dto.ItemDto;
import lk.ise.posre.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private ItemDao itemDao = new ItemDaoImpl();
    @Override
    public boolean saveItem(ItemDto dto) throws Exception {
        return itemDao.save(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws Exception {
        return itemDao.update(new Item(dto.getCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public ItemDto findItem(String id) throws Exception {
        Item item = itemDao.find(id);
        if (item!=null){
            return new ItemDto(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand());
        }
        return null;
    }

    @Override
    public boolean deleteItem(String id) throws Exception {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> findAllItems() throws Exception {
        List<Item> all = itemDao.findAll();
        List<ItemDto> dtos = new ArrayList<>();
        for (Item item :
                all) {
            dtos.add(new ItemDto(item.getCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));

        }
        return dtos;
    }

    @Override
    public List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        return itemDao.loadItemCodes();
    }

    @Override
    public boolean updateQty(String code, int qty) throws SQLException, ClassNotFoundException {
        return itemDao.updateQty(code,qty);
    }
}



