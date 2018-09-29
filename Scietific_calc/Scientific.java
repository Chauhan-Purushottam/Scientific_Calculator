/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Scietific_calc;

import java.util.Stack;


/**
 *
 * @author purusho
 */
public class Scientific extends javax.swing.JFrame {
    
    final static int GARBAGE_VAL=-999999;
    
    public static int factorial(int n)
   {
        int i,fact=1;
        for(i=1;i<=n;i++){
            fact*=i;
        }
        return fact;
    } 
    
    public static int isOperator(char c)
    {
        if(c=='^')
            return(1);
        if(c=='+' || c=='-'|| c=='*' || c=='/' || c=='√' || c=='d' ||
                c=='s' || c=='c' || c=='t' || c=='S' || c=='C' || c=='T' || c=='L' || c=='l'|| c=='!'|| c=='x'){
            return(1);
        }
        else{
            return(0);
        }
    }
    
    public static int isAlpha(char c){
        if(c=='L' || c=='l')
            return(1);
        if(c=='S' || c=='s')
            return(1);
        if(c=='C' || c=='c')
            return(1);
        if(c=='T' || c=='t' ||c=='d')
            return(1);
        if(c=='x' || c=='!' || c=='√')
            return(1);
       return(0);
    }
    public static int hasPrecedence(char c)
    {
        if(c=='(' || c==')'){
            return (4);}
        if(Scientific.isAlpha(c)==1)
        {
            return(3);
        }
        if(c=='*' || c=='^' || c=='/'){
            return(2);
        }
        if(c=='+' || c=='-'){
            return (1);
        }
        
        return 0;
    }
 
     public static String modify(String exp){
                
                 exp=exp.replaceAll("sin","s");
		 exp=exp.replaceAll("cos","c");
		 exp=exp.replaceAll("tan","t");
		 exp=exp.replaceAll("asin","S");
		 exp=exp.replaceAll("acos","C");
		 exp=exp.replaceAll("atan","T");
		 exp=exp.replaceAll("deg","d");
		 exp=exp.replaceAll("Log","L");
		 exp=exp.replaceAll("ln","l");
		 exp=exp.replaceAll("exp","x");
		 return(exp);
		 
	 }
    
    public static double evaluate(char op, double q,double p)
    {
        switch(op)
        {
            case '+':
                return (p + q);
            case '-':
                return (p - q);
            case '*':
                return (p * q);
            case '/':
            {
                if (q == 0){
                    throw new UnsupportedOperationException("DIVISON BY ZERO");
                }
			return (p / q);               
            }
            case '!':
                return((double)Scientific.factorial((int)q));
            case '^':
                return (Math.pow(p,q));
            case 's':
                return(Math.sin(q));
            case 'c':
                return(Math.cos(q));
            case 't':
                return(Math.tan(q));
            case 'S':
                return(Math.asin(q));
            case 'C':
                return(Math.acos(q));
            case 'T':
                return(Math.atan(q));
            case 'L':
                return(Math.log10(q));
            case 'l':
                return(Math.log(q));
            case 'x':
                return(Math.exp(q));
            case '√':
                return(Math.sqrt(q));
            case 'd':
                return(Math.toDegrees(q));
                
        }
        return 0;
    }
    
    public static double solution(String expr)
    {
        System.out.println(expr);
        char []array = expr.toCharArray();
        System.out.println(array[1]);
        Stack<Character> operator = new Stack<>();
        Stack<Double> operand = new Stack<>();
        for(int i = 0; i<array.length;i++)
        {
            if(array[0]=='-' && i==0){
                i+=2;
                StringBuilder str = new StringBuilder();
                while((i<array.length) && ((array[i] >= '0' && array[i] <= '9')||(array[i]=='.'))){
			str.append(array[i]);
			i++;
		}
                
                String x = "-"+str;
                System.out.println(x);
                operand.push(Double.parseDouble(x));
                 
                if(i>=array.length){
                    break;
                }
            }
            System.out.println("I= "+i);
            if(array[i]==' '){
                continue;
            }
            /*if(isOperator(array[i])==1){
                System.out.println("Operator");
                operator.push(array[i]);
            }*/
            if(array[i]>='0' && array[i]<='9'){
                StringBuilder numstr = new StringBuilder();
               System.out.println("num"+i);
                while((i<array.length) && ((array[i] >= '0' && array[i] <= '9')||(array[i]=='.'))){
                    numstr.append(array[i++]);
		}
               // System.out.println(numstr);
                operand.push(Double.parseDouble(numstr.toString()));
            }
            else if(array[i]=='('){
                        operator.push(array[i]);
                }
            
            else if(array[i]==')'){
                
                while(operator.peek()!='('){
                    char op = operator.pop();
                    if(Scientific.isAlpha(op)==1){
                        double result=evaluate(op, operand.pop(), 1);
                        System.out.println("RES"+result);
                        operand.push(result);
                    }
                    else{
                    double result = evaluate(op,operand.pop(),operand.pop());
                    operand.push(result);
                   }
                }
                operator.pop();
            }
            
            else if(Scientific.isOperator(array[i])==1){
                
                try{
                                       
                    while (!operator.empty() && hasPrecedence(array[i])<=hasPrecedence(operator.peek())
                             && operator.peek()!='('){
                        char op=operator.pop();
                        if(Scientific.isAlpha(op)==1){
                            double result=evaluate(op, operand.pop(), 1);
                            System.out.println("RES "+result);
                            operand.push(result);
                        }
                        else{
                            double result = evaluate(op,operand.pop(),operand.pop());
                            operand.push(result);
                        }
                    } 
                     operator.push(array[i]);
                 }
                catch(Exception e){
                     System.out.println(e);
                }
            }
            
        }
        try{
            while(!operator.empty()){
                char op = operator.pop();
                if(Scientific.isAlpha(op)==1){
                    double result=evaluate(op, operand.pop(), 1);
                    System.out.println("RES "+result);
                    operand.push(result);
                }
                else{
                    double result = evaluate(op,operand.pop(),operand.pop());
                    operand.push(result);
                }
                
                
            }
        }
        catch(Exception e){
            return(GARBAGE_VAL);
        }
        
        return operand.pop();
    }

    /**
     * Creates new form Scientific
     */
    public Scientific() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar3 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jbtnresult = new javax.swing.JTextField();
        jbtndiv = new javax.swing.JButton();
        jbtnc = new javax.swing.JButton();
        jbtnmul = new javax.swing.JButton();
        jbtnback = new javax.swing.JButton();
        jbtn7 = new javax.swing.JButton();
        jbtn8 = new javax.swing.JButton();
        jbtn9 = new javax.swing.JButton();
        jbtnsub = new javax.swing.JButton();
        jbtn4 = new javax.swing.JButton();
        jbtn5 = new javax.swing.JButton();
        jbtn6 = new javax.swing.JButton();
        jbtnadd = new javax.swing.JButton();
        jbtn1 = new javax.swing.JButton();
        jbtn2 = new javax.swing.JButton();
        jbtn3 = new javax.swing.JButton();
        jbtneql = new javax.swing.JButton();
        jbtnmod = new javax.swing.JButton();
        jbtn0 = new javax.swing.JButton();
        jbtndot = new javax.swing.JButton();
        jbtnplusmin = new javax.swing.JButton();
        jbtncaret = new javax.swing.JButton();
        jbtnlog = new javax.swing.JButton();
        jbtnln = new javax.swing.JButton();
        jbtnasin = new javax.swing.JButton();
        jbtnsqrt = new javax.swing.JButton();
        jbtnbrc = new javax.swing.JButton();
        jbtnbrcl = new javax.swing.JButton();
        jbtnacos = new javax.swing.JButton();
        jbtnfact = new javax.swing.JButton();
        jbtnsin = new javax.swing.JButton();
        jbtncos = new javax.swing.JButton();
        jbtnatan = new javax.swing.JButton();
        jbtninv = new javax.swing.JButton();
        jbtntan = new javax.swing.JButton();
        jbtnexp = new javax.swing.JButton();
        jbtndeg = new javax.swing.JButton();
        jbtnpi = new javax.swing.JButton();
        jbtne = new javax.swing.JButton();
        jbtnans = new javax.swing.JButton();
        jbtnroot2 = new javax.swing.JButton();
        jback1 = new javax.swing.JButton();
        jbtnexit = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        jMenu1.setText("File");
        jMenuBar2.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar3.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar3.add(jMenu4);

        jMenuItem4.setText("jMenuItem4");

        jMenuItem5.setText("jMenuItem5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(18, 245, 137));

        jbtnresult.setBackground(new java.awt.Color(92, 241, 241));
        jbtnresult.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jbtnresult.setText("0");
        jbtnresult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnresultActionPerformed(evt);
            }
        });

        jbtndiv.setText("/");
        jbtndiv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtndivActionPerformed(evt);
            }
        });

        jbtnc.setText("c");
        jbtnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtncActionPerformed(evt);
            }
        });

        jbtnmul.setText("*");
        jbtnmul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnmulActionPerformed(evt);
            }
        });

        jbtnback.setText("◀");
        jbtnback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnbackActionPerformed(evt);
            }
        });

        jbtn7.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn7.setText("7");
        jbtn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn7ActionPerformed(evt);
            }
        });

        jbtn8.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn8.setText("8");
        jbtn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn8ActionPerformed(evt);
            }
        });

        jbtn9.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn9.setText("9");
        jbtn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn9ActionPerformed(evt);
            }
        });

        jbtnsub.setText("-");
        jbtnsub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsubActionPerformed(evt);
            }
        });

        jbtn4.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn4.setText("4");
        jbtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn4ActionPerformed(evt);
            }
        });

        jbtn5.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn5.setText("5");
        jbtn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn5ActionPerformed(evt);
            }
        });

        jbtn6.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn6.setText("6");
        jbtn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn6ActionPerformed(evt);
            }
        });

        jbtnadd.setText("+");
        jbtnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnaddActionPerformed(evt);
            }
        });

        jbtn1.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn1.setText("1");
        jbtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn1ActionPerformed(evt);
            }
        });

        jbtn2.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn2.setText("2");
        jbtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn2ActionPerformed(evt);
            }
        });

        jbtn3.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn3.setText("3");
        jbtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn3ActionPerformed(evt);
            }
        });

        jbtneql.setText("=");
        jbtneql.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtneqlActionPerformed(evt);
            }
        });

        jbtnmod.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnmod.setText("%");
        jbtnmod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnmodActionPerformed(evt);
            }
        });

        jbtn0.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtn0.setText("0");
        jbtn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtn0ActionPerformed(evt);
            }
        });

        jbtndot.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtndot.setText(".");
        jbtndot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtndotActionPerformed(evt);
            }
        });

        jbtnplusmin.setText("±");
        jbtnplusmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnplusminActionPerformed(evt);
            }
        });

        jbtncaret.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jbtncaret.setText("^");
        jbtncaret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtncaretActionPerformed(evt);
            }
        });

        jbtnlog.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnlog.setText("Log");
        jbtnlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnlogActionPerformed(evt);
            }
        });

        jbtnln.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnln.setText("ln");
        jbtnln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnlnActionPerformed(evt);
            }
        });

        jbtnasin.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnasin.setText("asin");
        jbtnasin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnasinActionPerformed(evt);
            }
        });

        jbtnsqrt.setFont(new java.awt.Font("Waree", 1, 14)); // NOI18N
        jbtnsqrt.setText("√");
        jbtnsqrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsqrtActionPerformed(evt);
            }
        });

        jbtnbrc.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnbrc.setText("(");
        jbtnbrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnbrcActionPerformed(evt);
            }
        });

        jbtnbrcl.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnbrcl.setText(")");
        jbtnbrcl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnbrclActionPerformed(evt);
            }
        });

        jbtnacos.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnacos.setText("acos");
        jbtnacos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnacosActionPerformed(evt);
            }
        });

        jbtnfact.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnfact.setText("!");
        jbtnfact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnfactActionPerformed(evt);
            }
        });

        jbtnsin.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnsin.setText("sin");
        jbtnsin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnsinActionPerformed(evt);
            }
        });

        jbtncos.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtncos.setText("cos");
        jbtncos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtncosActionPerformed(evt);
            }
        });

        jbtnatan.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnatan.setText("atan");
        jbtnatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnatanActionPerformed(evt);
            }
        });

        jbtninv.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtninv.setText("1/x");
        jbtninv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtninvActionPerformed(evt);
            }
        });

        jbtntan.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtntan.setText("tan");
        jbtntan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtntanActionPerformed(evt);
            }
        });

        jbtnexp.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnexp.setText("exp");
        jbtnexp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnexpActionPerformed(evt);
            }
        });

        jbtndeg.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtndeg.setText("deg");
        jbtndeg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtndegActionPerformed(evt);
            }
        });

        jbtnpi.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnpi.setText("∏");
        jbtnpi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnpiActionPerformed(evt);
            }
        });

        jbtne.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtne.setText("e");
        jbtne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtneActionPerformed(evt);
            }
        });

        jbtnans.setText("Ans");
        jbtnans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnansActionPerformed(evt);
            }
        });

        jbtnroot2.setFont(new java.awt.Font("Waree", 1, 12)); // NOI18N
        jbtnroot2.setText("√2");
        jbtnroot2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnroot2ActionPerformed(evt);
            }
        });

        jback1.setText("Back / standard_mode");
        jback1.setDoubleBuffered(true);
        jback1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jback1ActionPerformed(evt);
            }
        });

        jbtnexit.setText("Exit");
        jbtnexit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnexitActionPerformed(evt);
            }
        });
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtnresult)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnmod, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn0, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtndot, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnplusmin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtneql, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnsub, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnback, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtndiv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnmul, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnexit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jback1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnpi, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtne, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnans, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnroot2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtninv, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtntan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnexp, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtndeg, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnfact, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnsin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtncos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnatan, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtnsqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnbrc, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnbrcl, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnacos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtncaret, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnlog, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnln, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnasin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jbtnresult, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnback, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtndiv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnmul, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnc, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtn8, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn9, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnsub, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtn5, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn6, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnadd, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtn2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtneql, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtn0, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtndot, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnplusmin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnmod, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnlog, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnln, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnasin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtncaret, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnbrc, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnbrcl, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnacos, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnsqrt, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtnsin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtncos, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnatan, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnfact, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtntan, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnexp, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtndeg, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtninv, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jbtne, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnans, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnroot2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtnpi, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnexit, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jback1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnmulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnmulActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Syntax error");
        }
        else
        {
            String input = jbtnresult.getText()+" * ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnmulActionPerformed

    private void jbtnsubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsubActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("-");
        }
        else
        {
            String input = jbtnresult.getText()+" - ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnsubActionPerformed

    private void jbtnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnaddActionPerformed
        // TODO add your handling code here:
       if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("+");
        }
        else
        {
            String input = jbtnresult.getText()+" + ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnaddActionPerformed

    private void jbtneqlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtneqlActionPerformed
        // TODO add your handling code here:
        StringBuilder x = new StringBuilder(800);
        String expr = jbtnresult.getText();
        x.append(expr);
        String str=Scientific.modify(x.toString());
        float result = (float)Scientific.solution(str.trim());
        System.out.println(result);
        if(result!=GARBAGE_VAL){
            if(result==0.0){
                jbtnresult.setText("0");}
            else{
                jbtnresult.setText(""+result);}
        }        
        else{
            jbtnresult.setText("Syntax Error");
        }
        
    }//GEN-LAST:event_jbtneqlActionPerformed

    private void jbtnplusminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnplusminActionPerformed
        // TODO add your handling code here:
        
        double val = Double.parseDouble(String.valueOf(jbtnresult.getText()));
        val = val * (-1);
        jbtnresult.setText(String.valueOf(val));
    }//GEN-LAST:event_jbtnplusminActionPerformed

    private void jbtnasinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnasinActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("asin( ");
        }
        else
        {
            String input = jbtnresult.getText()+"asin( ";
            jbtnresult.setText(input);
        }
        
    }//GEN-LAST:event_jbtnasinActionPerformed

    private void jbtnacosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnacosActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("acos( ");
        }
        else
        {
            String input = jbtnresult.getText()+"acos( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnacosActionPerformed

    private void jbtnatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnatanActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("atan( ");
        }
        else
        {
            String input = jbtnresult.getText()+"atan( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnatanActionPerformed

    private void jbtndegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtndegActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("deg( ");
        }
        else
        {
            String input = jbtnresult.getText()+"deg( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtndegActionPerformed

    private void jbtnroot2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnroot2ActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(Double.toString(Math.sqrt(2)));
       }
        else
       {
            String input = jbtnresult.getText()+Double.toString(Math.sqrt(2));
            jbtnresult.setText(input);
       }
    }//GEN-LAST:event_jbtnroot2ActionPerformed

    private void jbtncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtncActionPerformed
        // TODO add your handling code here:
        jbtnresult.setText("0");
    }//GEN-LAST:event_jbtncActionPerformed

    private void jbtnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnbackActionPerformed
        // TODO add your handling code here:
        String back = null;
        if(jbtnresult.getText().length() > 0)
        {
            StringBuilder stb = new StringBuilder(jbtnresult.getText());
            stb.deleteCharAt(jbtnresult.getText().length()-1);
            back = stb.toString();
            jbtnresult.setText(back);
        }
    }//GEN-LAST:event_jbtnbackActionPerformed

    private void jbtndivActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtndivActionPerformed
        // TODO add your handling code here:
       if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Syntax error");
        }
        else
        {
            String input = jbtnresult.getText()+" / ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtndivActionPerformed

    private void jbtn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn7ActionPerformed
        // TODO add your handling code here:
         if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn7.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn7.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn7ActionPerformed

    private void jbtn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn8ActionPerformed
        // TODO add your handling code here:
        
         if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn8.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn8.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn8ActionPerformed

    private void jbtn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn9ActionPerformed
        // TODO add your handling code here:
        
         if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn9.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn9.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn9ActionPerformed

    private void jbtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn4ActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn4.getText());
       }
        else
       {
            String input = jbtnresult.getText()+jbtn4.getText();
            jbtnresult.setText(input);
       }
    }//GEN-LAST:event_jbtn4ActionPerformed

    private void jbtn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn5ActionPerformed
        // TODO add your handling code here:
        
       if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn5.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn5.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn5ActionPerformed

    private void jbtn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn6ActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn6.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn6.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn6ActionPerformed

    private void jbtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn1ActionPerformed
        // TODO add your handling code here:
        
         if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn1.getText());
        }
        else 
        {
            String input = jbtnresult.getText()+jbtn1.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn1ActionPerformed

    private void jbtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn2ActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn2.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn2.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn2ActionPerformed

    private void jbtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn3ActionPerformed
        // TODO add your handling code here:
        
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn3.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn3.getText();
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtn3ActionPerformed

    private void jbtn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtn0ActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText(jbtn0.getText());
        }
        else
        {
            String input = jbtnresult.getText()+jbtn0.getText();
            jbtnresult.setText(input);
        }

    }//GEN-LAST:event_jbtn0ActionPerformed

    private void jbtndotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtndotActionPerformed
        // TODO add your handling code here:
        
        if(!jbtnresult.getText().contains("."))
        {
            jbtnresult.setText(jbtnresult.getText()+jbtndot.getText());
        }
    }//GEN-LAST:event_jbtndotActionPerformed

    private void jbtncaretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtncaretActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Syntax Error");
        }
        else
        {
            String input = jbtnresult.getText()+" ^ ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtncaretActionPerformed

    private void jbtnlogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnlogActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Log( ");
        }
        else
        {
            String input = jbtnresult.getText()+"Log( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnlogActionPerformed

    private void jbtnlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnlnActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("ln( ");
        }
        else
        {
            String input = jbtnresult.getText()+"ln( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnlnActionPerformed

    private void jbtnexitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnexitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jbtnexitActionPerformed

    private void jbtnbrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnbrcActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("( ");
        }
        else
        {
            StringBuilder stb = new StringBuilder(jbtnresult.getText());
            
            if(Scientific.isOperator(stb.charAt(jbtnresult.getText().length()-2))==1)
            {
                String input = jbtnresult.getText()+" ( ";
                jbtnresult.setText(input);
            }
            else{
                jbtnresult.setText("Syntax Error!");
            }
            
        }
        
    }//GEN-LAST:event_jbtnbrcActionPerformed

    private void jback1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jback1ActionPerformed
        // TODO add your handling code here:
        Scientific_cal sc = new Scientific_cal();
        sc.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jback1ActionPerformed

    private void jbtnpiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnpiActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
           jbtnresult.setText(Double.toString(Math.PI));
       }
        else{
            String input = jbtnresult.getText()+Double.toString(Math.PI);
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtnpiActionPerformed

    private void jbtneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtneActionPerformed
        // TODO add your handling code here:
         if(jbtnresult.getText().equals("0")){
           jbtnresult.setText(Double.toString(Math.E));
       }
        else{
            String input = jbtnresult.getText()+Double.toString(Math.E);
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtneActionPerformed

    private void jbtnmodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnmodActionPerformed
        // TODO add your handling code here:
        String val = jbtnresult.getText();
        double num = Double.parseDouble(val);
        num*=(0.01);
        jbtnresult.setText(Double.toString(num));
        
    }//GEN-LAST:event_jbtnmodActionPerformed

    private void jbtnresultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnresultActionPerformed
        // TODO add your handling code here:
        StringBuilder exp=new StringBuilder(800);   
        String temp=jbtnresult.getText();

        exp.append(temp);
        float ans=0f;

        //   REDUCING THE COMPLEX EQUATION

        String str=Scientific.modify(exp.toString());
        float result=(float)Scientific.solution(str.trim());
        if(result!=-123456){
            //System.out.println(res);
            jbtnresult.setText(""+result);
        }
        
        
    }//GEN-LAST:event_jbtnresultActionPerformed

    private void jbtnansActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnansActionPerformed
        // TODO add your handling code here:
        StringBuilder x = new StringBuilder(800);
        String expr = jbtnresult.getText();
        x.append(expr);
        String str=Scientific.modify(x.toString());
        float result = (float)Scientific.solution(str.trim());
        if(result!=GARBAGE_VAL){
          jbtnresult.setText(jbtnresult.getText()+"Ans = "+result);  
        }        
        else{
            jbtnresult.setText("Syntax Error");
        }
        
    }//GEN-LAST:event_jbtnansActionPerformed

    private void jbtnsinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsinActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("sin( ");
        }
        else
        {
            String input = jbtnresult.getText()+"sin( ";
            jbtnresult.setText(input);
        }
        
    }//GEN-LAST:event_jbtnsinActionPerformed

    private void jbtncosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtncosActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("cos( ");
        }
        else
        {
            String input = jbtnresult.getText()+"cos( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtncosActionPerformed

    private void jbtntanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtntanActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("tan( ");
        }
        else
        {
            String input = jbtnresult.getText()+"tan( ";
            jbtnresult.setText(input);
        }
    }//GEN-LAST:event_jbtntanActionPerformed

    private void jbtnbrclActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnbrclActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Syntax error");
       }
        else
       {
            String input = jbtnresult.getText()+" ) ";
            jbtnresult.setText(input);
       }
    }//GEN-LAST:event_jbtnbrclActionPerformed

    private void jbtnsqrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnsqrtActionPerformed
        // TODO add your handling code here:
         if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("√( ");
       }
        else
       {
            String input = jbtnresult.getText()+" √( ";
            jbtnresult.setText(input);
       }
        
    }//GEN-LAST:event_jbtnsqrtActionPerformed

    private void jbtnfactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnfactActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("1");
       }
        else
       {
            String input = jbtnresult.getText()+" ! ";
            jbtnresult.setText(input);
       }
    }//GEN-LAST:event_jbtnfactActionPerformed

    private void jbtninvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtninvActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("Syntax Error ");
       }
        else
       {
            String input = jbtnresult.getText()+"^(-1)";
            jbtnresult.setText(input);
       }
    }//GEN-LAST:event_jbtninvActionPerformed

    private void jbtnexpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnexpActionPerformed
        // TODO add your handling code here:
        if(jbtnresult.getText().equals("0")){
            jbtnresult.setText("exp( ");
       }
        else
       {
            String input = jbtnresult.getText()+"exp( ";
            jbtnresult.setText(input);
       }
        
    }//GEN-LAST:event_jbtnexpActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Scientific.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Scientific.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Scientific.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Scientific.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Scientific().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuBar jMenuBar3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JButton jback1;
    private javax.swing.JButton jbtn0;
    private javax.swing.JButton jbtn1;
    private javax.swing.JButton jbtn2;
    private javax.swing.JButton jbtn3;
    private javax.swing.JButton jbtn4;
    private javax.swing.JButton jbtn5;
    private javax.swing.JButton jbtn6;
    private javax.swing.JButton jbtn7;
    private javax.swing.JButton jbtn8;
    private javax.swing.JButton jbtn9;
    private javax.swing.JButton jbtnacos;
    private javax.swing.JButton jbtnadd;
    private javax.swing.JButton jbtnans;
    private javax.swing.JButton jbtnasin;
    private javax.swing.JButton jbtnatan;
    private javax.swing.JButton jbtnback;
    private javax.swing.JButton jbtnbrc;
    private javax.swing.JButton jbtnbrcl;
    private javax.swing.JButton jbtnc;
    private javax.swing.JButton jbtncaret;
    private javax.swing.JButton jbtncos;
    private javax.swing.JButton jbtndeg;
    private javax.swing.JButton jbtndiv;
    private javax.swing.JButton jbtndot;
    private javax.swing.JButton jbtne;
    private javax.swing.JButton jbtneql;
    private javax.swing.JButton jbtnexit;
    private javax.swing.JButton jbtnexp;
    private javax.swing.JButton jbtnfact;
    private javax.swing.JButton jbtninv;
    private javax.swing.JButton jbtnln;
    private javax.swing.JButton jbtnlog;
    private javax.swing.JButton jbtnmod;
    private javax.swing.JButton jbtnmul;
    private javax.swing.JButton jbtnpi;
    private javax.swing.JButton jbtnplusmin;
    private javax.swing.JTextField jbtnresult;
    private javax.swing.JButton jbtnroot2;
    private javax.swing.JButton jbtnsin;
    private javax.swing.JButton jbtnsqrt;
    private javax.swing.JButton jbtnsub;
    private javax.swing.JButton jbtntan;
    // End of variables declaration//GEN-END:variables
}
