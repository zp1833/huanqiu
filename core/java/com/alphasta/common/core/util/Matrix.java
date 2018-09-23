/*
 * File Name:Matrix.java
 * Author:Sam Lee
 * Last Updater:Sam Lee
 * Last Update Time:2004-3-13
 */
package com.alphasta.common.core.util;

import java.util.Arrays;

/**
 * 
 * <p>
 * Title:Matrix
 * </p>
 * <p>
 * Description: 矩阵类，用于实现常用的矩阵运算
 * </p>
 * @version 1.0
 */
public class Matrix implements Cloneable {
  /**
   * 浮点数判断所用的精度
   */
  public static final double PRECISION = 0.000000001d;
  /**
   * Matrix body
   */
  private double[][] body;
  /**
   * Count of column
   */
  private int col = 0;
  /**
   * Count of row
   */
  private int row = 0;
  /**
   * 行列式的值（如果矩阵是方阵）
   */
  private double squareMatrixValue = 0;
  /**
   * 逆转矩阵
   */
  private Matrix inverse = null;

  /**
   * 构造器，传入矩阵数据
   */
  public Matrix(double[][] data) {
    col = data[0].length;
    row = data.length;
    body = new double[row][col];
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        body[i][j] = data[i][j];
      }
    }
  }

  /**
   * 返回矩阵的行数
   */
  public int getRowCount() {
    return row;
  }

  /**
   * 返回矩阵的列数
   */
  public int getColCount() {
    return col;
  }

  /**
   * 返回矩阵的一行
   * @param rowIndex int 行号,第一行是0，第二行是1..
   * @return Vector
   */
  public Vector getRow(int rowIndex) {
    double[] rowData = new double[col];
    for (int i = 0; i < col; i++) {
      rowData[i] = body[rowIndex][i];
    }
    return new Vector(rowData);
  }

  /**
   * 返回矩阵的一列
   * @param colIndex int 列号,第一列是0，第二列是1..
   * @return Vector
   */
  public Vector getCol(int colIndex) {
    double[] colData = new double[row];
    for (int i = 0; i < row; i++) {
      colData[i] = body[i][colIndex];
    }

    return new Vector(colData);
  }

  /**
   * Clone自身
   * @return Object 包含同样数据的Matrix对象
   */
  public Object clone() {
    double[][] data = new double[col][row];
    for (int i = 0; i < col; i++) {
      for (int j = 0; j < row; j++) {
        data[i][j] = body[i][j];
      }
    }

    return new Matrix(data);
  }

  /**
   * 返回矩阵中的一个元素
   * @param rowIndex int 行号,第一行是0，第二行是1..
   * @param colIndex int 列号,第一列是0，第二列是1..
   * @return double 矩阵中的元素
   */
  public double dataAt(int rowIndex, int colIndex) {
    if (rowIndex < 0 || rowIndex >= row || colIndex < 0 || colIndex >= col) {
      throw new IndexOutOfBoundsException(
          "Matrix Error: The index of row or col is Out of bounds.");
    }

    return body[rowIndex][colIndex];
  }

  /**
   * 设置单元的数据
   * @param data double 新数据
   * @param rowIndex int 行号
   * @param colIndex int 列号
   */
  public void setAt(double data, int rowIndex, int colIndex) {
    if (rowIndex < 0 || rowIndex >= row || colIndex < 0 || colIndex >= col) {
      throw new IndexOutOfBoundsException(
          "Matrix Error: The index of row or col is Out of bounds.");
    }
    body[rowIndex][colIndex] = data;
  }

  /**
   * 矩阵中的两个元素互换
   * @param i int 元素1的行号,第一行是0，第二行是1..
   * @param j int 元素1的列号,第一列是0，第二列是1..
   * @param oi int 元素2的行号,第一行是0，第二行是1..
   * @param oj int 元素2的列号,第一列是0，第二列是1..
   */
  public void swap(int i, int j, int oi, int oj) {
    if (i >= 0 && i < row && j >= 0 && j < col && oi >= 0 && oi < row
        && oj >= 0 && oj < col) {
      double swapper = body[i][j];
      body[i][j] = body[oi][oj];
      body[oi][oj] = swapper;
    }
  }

  /**
   * 矩阵相乘
   * @param m 被乘数矩阵
   * @return Matrix 结果矩阵
   */
  public Matrix multiply(Matrix m) {
    if (col != m.getRowCount()) {
      throw new IllegalArgumentException("Matrix Error:Can't multiply,"
          + " Number of column must be equals to rows");
    }

    double[][] res = new double[row][m.getColCount()];

    for (int i = 0; i < row; i++) {
      Vector aV = this.getRow(i);
      for (int j = 0; j < m.getColCount(); j++) {
        Vector bV = m.getCol(j);
        res[i][j] = aV.multiply(bV);
      }
    }
    

    return new Matrix(res);
  }

  /**
   * 求解行列式
   * @return double 行列式的值
   */
  public double squareValue() {
    if (this.squareMatrixValue == 0) {
      squareMatrix();
    }

    return this.squareMatrixValue;
  }

  /**
   * 求解线性方程组。 <br>
   * 设线性方程组的系数矩阵为[B]，常数矩阵为[W]，未知数矩阵为[X]。则方程组表示为:<br>
   * (1)[B].[X]=[W]<br>
   * 已知[B]的逆矩阵为[B-1]，有(2)[B].[B-1]=[B-1].[B]=1<br>
   * 等式(1)的两侧左乘[B-1]，[B-1].[B].[X]=[B-1].[W]→[X]=[B-1].[W]
   * @param fx 常数向量
   * @return Vector 未知数向量
   */
  public Vector solution(Vector fx) {
    // 常数相量转化为常数矩阵
    int cols = fx.getLength();
    double[][] inData = new double[cols][1];
    for (int i = 0; i < cols; i++) {
      inData[i][0] = fx.dataAt(i);
    }
    // 求系数矩阵的逆矩阵
    Matrix in = new Matrix(inData);
    if (this.inverse == null) {
      this.squareMatrix();
    }
    // 计算未知数
    //double[] out = new double[cols];
    Matrix x = this.inverse.multiply(in);

    return x.toVector();
  }

  /**
   * 将矩阵转化为相量
   * @return Vector 转化得到的相量
   */
  public Vector toVector() {
    double[] data = new double[col * row];
    int index = 0;
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        data[index] = body[i][j];
        index++;
      }
    }

    return new Vector(data);
  }

  /**
   * 求逆矩阵
   * @return Matrix 当前矩阵的逆矩阵
   */
  public Matrix inverse() {
    if (this.inverse == null) {
      squareMatrix();
    }

    return inverse;
  }

  /**
   * 求逆转矩阵和方阵的值 算法介绍-全选主元高斯-约旦法。 
   * 高斯-约旦法（全选主元）求逆的步骤如下： 首先，对于 k阶方阵 从 0 到 n - 1
   * 作如下几步： 从第 k 行、第 k 列开始的右下角子阵中选取绝对值最大的元素，并记住次元素所在的行号和
   * 列号，在通过行交换和列交换将它交换到主元素位置上。这一步称为全选主元。 然后，进行如下操作：<br>
   * <pre>
   * m(k, k) = 1 / m(k, k)
   * m(k, j) = m(k, j) * m(k, k)，j = 0, 1, ..., n-1；j != k
   * m(i, j) = m(i, j) - m(i, k) * m(k, j)，i, j = 0, 1, ..., n-1；i, j != k 
   * m(i, k) = -m(i, k) * m(k, k)，i = 0, 1, ..., n-1；i != k
   * </pre>
   * 最后，根据在全选主元过程中所记录的行、列交换的信息进行恢复，恢复的原则如下：<br>
   * 在全选主元过程中，先交换的行（列）后进行恢复；原来的行（列）交换用列（行）交换来恢复。
   */
  private void squareMatrix() {
    if (this.col != this.row) {
      throw new ArithmeticException("Number of column must be"
          + " equals to rows.");
    }
    int phalanx = col;

    Matrix x = (Matrix) clone();

    int[] is = new int[phalanx];
    int[] js = new int[phalanx];
    double fDet = 1.0d;
    int f = 1;

    for (int k = 0; k < phalanx; k++) {
      // 第一步，全选主元
      double fMax = 0.0d;
      for (int i = k; i < phalanx; i++) {
        for (int j = k; j < phalanx; j++) {
          double fx = Math.abs(x.dataAt(i, j));
          if (fx > fMax) {
            fMax = fx;
            is[k] = i;
            js[k] = j;
          }
        }
      }
      if (Math.abs(fMax) < PRECISION) {
        throw new ArithmeticException("Matrix error:Cant' get inverse.");
      }

      if (is[k] != k) {
        f = -f;
        x.swap(k, 0, is[k], 0);
        x.swap(k, 1, is[k], 1);
        x.swap(k, 2, is[k], 2);
        x.swap(k, 3, is[k], 3);
      }
      if (js[k] != k) {
        f = -f;
        x.swap(0, k, 0, js[k]);
        x.swap(1, k, 1, js[k]);
        x.swap(2, k, 2, js[k]);
        x.swap(3, k, 3, js[k]);
      }

      // 计算行列值
      fDet *= x.dataAt(k, k);
      // 计算逆矩阵
      // 第二步m(k, k) = 1.0f / m(k, k);
      x.setAt(1.0f / x.dataAt(k, k), k, k);

      // 第三步m(k, j) *= m(k, k);
      for (int j = 0; j < phalanx; j++) {
        if (j != k) {
          x.setAt((x.dataAt(k, j)) * (x.dataAt(k, k)), k, j);
        }
      }
      // 第四步
      for (int i = 0; i < phalanx; i++) {
        if (i != k) {
          for (int j = 0; j < phalanx; j++) {
            if (j != k) {
              // m(i, j) = m(i, j) - m(i, k) * m(k, j);
              x.setAt(x.dataAt(i, j) - x.dataAt(i, k) * x.dataAt(k, j), i, j);
            }
          }
        }
      }
      // 第五步
      for (int i = 0; i < phalanx; i++) {
        if (i != k) {
          // m(i, k) *= -m(k, k);
          x.setAt(x.dataAt(i, k) * (-x.dataAt(k, k)), i, k);
        }
      }
    }
    // 根据在全选主元过程中所记录的行、列交换的信息进行恢复
    for (int k = phalanx - 1; k >= 0; k--) {
      if (js[k] != k) {
        x.swap(k, 0, js[k], 0);
        x.swap(k, 1, js[k], 1);
        x.swap(k, 2, js[k], 2);
        x.swap(k, 3, js[k], 3);
      }
      if (is[k] != k) {
        x.swap(0, k, 0, is[k]);
        x.swap(1, k, 1, is[k]);
        x.swap(2, k, 2, is[k]);
        x.swap(3, k, 3, is[k]);
      }
    }

    this.squareMatrixValue = fDet * f;
    this.inverse = x;
  }

  /**
   * 求解当前矩阵的转置矩阵
   * @return Matrix 转置矩阵
   */
  public Matrix transpose() {
    double [][]tp = new double[col][row];
    for (int i = 0; i < col; i++) {
      for (int j = 0; j < row; j++) {
        tp[i][j] = body[j][i];
      }
    }

    return new Matrix(tp);
  }

  /**
   * 打印矩阵
   */
  public void print() {
    System.out.print(this.toString());
  }

  /**
   * 返回矩阵的字符串表示
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        buf.append(body[i][j]);
        buf.append("    ");
      }
      buf.append('\n');
    }

    return buf.toString();
  }
  
  /**
   * @see Object#hashCode()
   */
  @Override
  public int hashCode() {
    return 31 + Arrays.hashCode(body);
  }
  
  /**
   * @see Object#equals(Object)
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }

    if (!(o instanceof Matrix)) {
      return false;
    }

    Matrix m = (Matrix) o;
    if (m.getColCount() != this.col || m.getRowCount() != this.row) {
      return false;
    }

    boolean eq = true;

    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        double zero = body[i][j] - m.dataAt(i, j);
        if (!(zero >= -PRECISION && zero < PRECISION)) {
          eq = false;
          break;
        }
      }
      if (!eq) {
        break;
      }
    }
    
    return eq;
  }

  /**
   *  Test
   */
  public static void main(String[] args) throws Exception {
    //double [][]data = { { 3, 1, 7, 1 }, { 1, 4, 6, 8 }, { 5, 1, 5, 9 },
    //    { 8, 9, 4, 2 } };
    /*
     * double y[][] = {{3},{5},{6},{9}}; Matrix yd = new Matrix(y); Matrix m =
     * new Matrix(data); Matrix tp = m.transpose(); tp.print(); Matrix mtp =
     * tp.multiply(m); Matrix tpy = tp.multiply(yd); //Matrix myp_1 =
     * mtp.inverse(); Vector b = mtp.solution(tpy.toVector()); b.print();
     * m.solution(yd.toVector()).print();
     */
    // m.print();
    // Matrix v = m.inverse();
    // Matrix vv = v.inverse();
    // vv.print();
    // System.out.println(vv.equals(m));
    // begin__liyanhui
    double [][]dataMe = {
    // {19,3.2,87},
        { 14, 2.7, 88 }, { 19, 3.7, 104 }, { 17.5, 2.9, 107 } };
    double [][]y = { { 6.7 }, { 7.1 }, { 8 } };
    Matrix xMatrix = new Matrix(dataMe);
    Matrix yMatrix = new Matrix(y);
    Matrix tpX = xMatrix.transpose();
    Matrix tpXX = tpX.multiply(xMatrix);
    Matrix invTpXX = tpXX.inverse();
    Matrix invTpXXtpX = invTpXX.multiply(tpX);
    Matrix bb = invTpXXtpX.multiply(yMatrix);
    Vector b = bb.toVector();
    // double y[][] = {6.7,7.1,8};
    /*
     * Vector yy = new Vector(y); Matrix m = new Matrix(dataMe);
     * System.out.println("矩阵"); m.print(); Matrix tp = m.transpose();//转置
     * System.out.println("转置"); tp.print();
     * 
     * Matrix tpm = tp.multiply(m);//转置 * 矩阵 System.out.println("转置 * 矩阵");
     * tpm.print();
     * 
     * Matrix v = tpm.inverse();//逆矩阵 System.out.println("转置 * 矩阵 逆矩阵");
     * v.print();
     * 
     * Matrix tpmtp = v.multiply(tp); System.out.println("(转置 * 矩阵)
     * 的逆矩阵 × 转置");
     * tpmtp.print();
     * 
     * Vector b = tpmtp.solution(yy); b.print();
     * 
     * System.out.println(""); //old Vector b = v.solution(yy); // b.print();
     */
    double salaryRate = 0;
    int i;
    double[] known = new double[3];
    known[0] = 20;
    known[1] = 4.1;
    known[2] = 105;

    for (i = 0; i < b.getLength(); i++) {
      salaryRate = b.dataAt(i) * known[i] + salaryRate;
    }
    System.out.print(salaryRate);

    // end__liyanhui

    /*
     * double data[][] = { {3,1,7,1}, {1,4,6,8}, {5,1,5,9}, {8,9,4,2} }; double
     * data2[] = { 1,2,-4,7 };
     * 
     * Matrix m = new Matrix(data); Vector v = new Vector(data2); v.print();
     * Vector x = m.solution(v);
     * 
     * x.print();
     * 
     * double d[] = new double[4]; for(int i=0;i<4;i++) { double s = 0.0d;
     * for(int j=0;j<4;j++) { s+=data[i][j]*x.dataAt(j); }
     * System.out.println(s); d[i]=s; } Vector ve = new Vector(d);
     * System.out.println(v.equals(ve));
     */
    /*
     * double data2[][] = { {1,-1,3}, {2,-1,4}, {-1,2,-4} };
     */
    /*
     * double data2[][] = { {2,-1,3}, {-1,2,1}, {4,1,2} }; Matrix m = new
     * Matrix(data2); m.print(2); System.out.println(m.squareValue()); Matrix
     * inv = m.inverse(); inv.print(); Matrix t = inv.inverse(); t.print(2);
     */
    // System.out.println(m.matrixValue());
    // m.print();
    // Matrix inv = m.inverse();
    // m.print();
    /*
     * double w[][] = { {1},{0},{-1} }; Matrix b = new Matrix(w); Matrix c =
     * inv.multiply(b); c.print();
     */
    // m.print();
    // System.out.println("**********************");
    // Matrix x = m.inverse();
    // x.print(2);
    // System.out.println("**********************");
    // m = x.inverse();
    // m.print(2);
    /*
     * double data2[][] = { {1,1,1,1}, {1,1,1,1}, {1,1,1,1}, {1,1,1,1} };
     */
    // Matrix m2 = new Matrix(data2);
    // m.multiply(m2).print();
    /*
     * System.out.println(m.getRowCount()); System.out.println(m.getColCount());
     * m.print(); System.out.println("\n"); m.getCol(2).print();
     */
  }

  /**
   * 
   * <p>
   * Title: Vector
   * </p>
   * <p>
   * Description: 矢量类，用于实现常见的矢量运算
   * </p>
   * 
   * @version 1.0
   */
  public static class Vector implements Cloneable {
    /**
     * Body of vector
     */
    private double[] body;
    /**
     * Length of vector
     */
    private int length;

    /**
     * Constructor.
     */
    public Vector(double[] data) {
      length = data.length;
      body = new double[length];
      for (int i = 0; i < length; i++) {
        body[i] = data[i];
      }
    }

    /**
     * 打印
     */
    public void print() {
      System.out.println(this.toString());
    }

    /**
     * Clone自身
     * @return Object 包含同样数据的Matrix对象
     */
    public Object clone() {
      double[] data = new double[length];
      for (int i = 0; i < length; i++) {
        data[i] = body[i];
      }

      return new Vector(data);
    }

    /**
     * 返回矩阵的阶
     */
    public int getLength() {
      return length;
    }

    /**
     * 返回矩阵的一个元素
     * @param index int 元素下标,first is 0...
     * @return double 矩阵的一个元素
     */
    public double dataAt(int index) {      
        return body[index];      
    }

    /**
     * 计算矢量的乘积
     * @param v Vector 被乘数
     * @return double 结果
     */
    public double multiply(Vector v) {
      if (length == v.getLength()) {
        double res = 0.0d;
        for (int i = 0; i < length; i++) {
          res += body[i] * v.dataAt(i);
        }

        return res;
      } else {
        throw new IllegalArgumentException("Length of multiplicand must be"
            + " equals to length of multiplicator");
      }
    }

    /**
     * 返回矢量的字符串表示
     */
    public String toString() {
      StringBuffer buf = new StringBuffer();

      for (int i = 0; i < length; i++) {
        buf.append(body[i]);
        buf.append("     ");
      }

      return buf.toString();
    }
    
    /**
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + Arrays.hashCode(body);
      result = 31 * result + length;
      return result;
    }
    
    /**
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Vector other = (Vector) obj;
      if (!Arrays.equals(body, other.body)) {
        return false;
      }
      if (length != other.length) {
        return false;
      }
      return true;
    }
  }

}
