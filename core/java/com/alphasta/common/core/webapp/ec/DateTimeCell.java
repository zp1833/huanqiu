package com.alphasta.common.core.webapp.ec;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.table.cell.AbstractCell;

import com.alphasta.common.Constants;
import com.alphasta.common.core.util.DateUtil;
import com.alphasta.common.core.util.ResourceBundleUtil;

/**
 * 用于显示日期和时间的Cell
 * @author Sam
 * 
 */
public class DateTimeCell extends AbstractCell {
  /**
   * @see AbstractCell#getHtmlDisplay(TableModel, Column)
   */
  @Override
  protected final String getCellValue(TableModel model, Column column) {
    Object obj = column.getValue();
    if (obj == null) {
      return StringUtils.EMPTY;
    }
    if (obj instanceof Date) {      
      // 从系统资源文件中取得时间格式
      String format = ResourceBundleUtil.getString(Constants.RESOURCE_BUNDLE,
            "datetime.format", "yyyy-MM-dd hh:mm");
      
      return DateUtil.getDateTime(format, (Date) obj);
    }
    return column.getValueAsString();
  }

}
