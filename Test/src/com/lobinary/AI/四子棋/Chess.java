package com.lobinary.AI.������;

public class Chess {
 private int Row,Col;
 private int rowPlace[];
 public int chess[][];
 private int total;
 
 
	public Chess(int Row,int Col) {//�������ӳ�ʼ��
		// TODO Auto-generated constructor stub
		chess = new int [Row][Col];
		rowPlace = new int [Col];
		this.Row=Row;
		this.Col=Col;
		for(int i=0; i<Row; i++){
			for(int j=0; j<Col; j++){
				chess[i][j]=0;
			}
		}
		for(int i=0; i<Col; i++){
			rowPlace[i] = (Row-1); 
		}
		total = Row*Col;
	}
	
	public void AddChess(int Col){//��һ��
		if(rowPlace[Col]>0){
			rowPlace[Col]--;
			total--;
		}
	}
	
	public int place(int Col){//����Ӧ������к�
		return rowPlace[Col];
	}

	public void setChess(int player,int row,int col){
		    
         		chess[row][col]=player;
	}
	
	//**********************************
	public int ranPlace(){//�������
		double temp = Math.random()*Col;
		int num = new Double(temp).intValue();
		return num;
	}
	
	public boolean IsDraw(){//�ж��Ƿ�ƽ��
		if(total==0){
			return true;
		}
		return false;
	}
	
	public boolean IsWin(int player,int x, int y){//�ж��Ƿ�Ӯ
		int count;
		int left,right,up,down;
		left = right = x;
		up = down = y;
		//�����ж�
		count = 0;
		while(right<Col&&chess[right][y]==player){
			right++;
			count++;			
		}
		while(left>=0&&chess[left][y]==player){
			
			left--;
			count++;
		}
		if((count-1)>=4){
			return true;
		}
		//�����ж�
		count = 0;
		while(up<Row&&chess[x][up]==player){
			
			up++;
			count++;
		}
		while(down>=0&&chess[x][down]==player){
			
			down--;
			count++;
		}
		if((count-1)>=4){
			return true;
		}
		//�ж���б
		count = 0;
		left = right = x;
		up = down = y;
		while(down>=0&&right<Col&&chess[right][down]==player){
            right++;
            down--;
			count++;
		}
		left = right = x;
		up = down = y;
		while(left>=0&&up<Row&&chess[left][up]==player){
			left--;
			up++;
			count++;
		}
		if((count-1)>=4){
			return true;
		}
		//�ж���б
		count = 0;
		left = right = x;
		up = down = y;
		while(left>=0&&down>=0&&chess[left][down]==player){
			down--;
			left--;
			count++;
		}
		left = right = x;
		up = down = y;
		while(up<Row&&right<Col&&chess[right][up]==player){
			up++;
			right++;
			count++;
		if((count-1)>=4){
			return true;
		}
		System.out.println("chess"+count);
		}

		return false;
}
	
	//**********************************************
	public int AIplace()//����������кţ��������λ��
	{
		for(int num=4;num>0;num--)
		{
				for(int i=0;i<Col;i++)
				{
					setChess(1,place(i),i);//�������ÿ����һ��
					if(MaxNum(place(i),i,1)==num)
					{
						setChess(0,place(i),i);
						//test
						if(place(i)>7)
						{
							System.out.println("set out"+place(i));
							continue;
						}
						return i;//�������λ��
					}
					setChess(0,place(i),i);//����֮ǰ���������
				}
				
				for(int i=0;i<Col;i++)
				{
					setChess(2,place(i),i);//�����Լ�ÿ����һ��
					if(MaxNum(place(i),i,2)==num)
					{
						setChess(0,place(i),i);
						if(place(i)>7)
						{
							System.out.println("set out +1"+place(i));
							continue;
						}
						return i;//�������λ��
					}
					setChess(0,place(i),i);//����֮ǰ���������
				}
			
		}					
		return 0;
	}

	private int MaxNum(int x, int y, int player) {
		// TODO Auto-generated method stub
		
		int pxleft,pxright,pyup,pydown;
		pxleft=pxright=x;
		pyup=pydown=y;
		int num1,num2,num3,num4;//������б��б���Ӹ���
		
///////////////////////////////////////////////////
		//�����ж�
		num2=0;
		while(pxleft>=0 && chess[pxleft][y]==player)
		{
			pxleft--;
			num2++;
		}
		while(pxright<Row && chess[pxright][y]==player)
		{
			pxright++;
			num2++;
		}
		num2--;
////////////////////////////////////////////////////////////////		
//		//�����ж�
		num1=0;
		while(pyup>=0 && chess[x][pyup]==player)
		{
			pyup--;
			num1++;
		}
		while(pydown<Col && chess[x][pydown]==player)
		{
			pydown++;
			num1++;
		}
		num1--;
		
////////////////////////////////////////////////		
//		б�����ж�
		num3=0;
		pxleft=pxright=x;
		pyup=pydown=y;
		while(pxleft>=0 && pyup>=0 && chess[pxleft][pyup]==player)
		{
			pxleft--;
			pyup--;
			num3++;
		}
		
		pxleft=pxright=x;
		pyup=pydown=y;
		while(pxright<Row && pydown<Col && chess[pxright][pydown]==player)
		{
			pxright++;
			pydown++;
			num3++;
		}
		num3--;
		
//////////////////////////////////////////////////////		
//		б�����ж�
		
		num4=0;
		pxleft=pxright=x;
		pyup=pydown=y;
		while(pxright<Row && pyup>=0 && chess[pxright][pyup]==player)
		{
			pxright++;
			pyup--;
			num4++;
		}
		
		pxleft=pxright=x;
		pyup=pydown=y;
		while(pxleft>=0 && pydown<Col && chess[pxleft][pydown]==player)
		{
			pxleft--;
			pydown++;
			num4++;
		}
		num4--;
		
		return Max(num1,num2,num3,num4);
		////////////////////////////
	}

	private int Max(int num1, int num2, int num3, int num4) {
		// TODO Auto-generated method stub
		int max = num1;
		if(num1<num2){
		     max = num2;	
		}
		if(max<num3){
			max = num3;
		}
		if(max<num4){
			max = num4;
		}
		return max;
	}
	}

