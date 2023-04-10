package lk.ise.posre.bo.custom;

import lk.ise.posre.bo.SuperBo;
import lk.ise.posre.dto.OrderDetailDto;
import lk.ise.posre.dto.OrderDto;
import lk.ise.posre.entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {

    public boolean saveOrder(OrderDto dto, List<OrderDetailDto> orderDetail) throws Exception;
    public String generateOrderId() throws SQLException, ClassNotFoundException;

}
