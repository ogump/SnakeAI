package AI;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import Mode.Node;
import Mode.Snake;



public class SnakeAi {
	/**
	 * Make it work
	 * 直接走曼哈顿距离,注意：由于Node类重写了equals方法，Snake类的move方法里的s.remove在删除一个末尾节点时会删除2个节点，
	 * 导致蛇身重复的地方不会画出来，显示蛇身体断了，如需修复可以删除或注释equals方法
	 * @param s
	 * @param f
	 * @param dir
	 * @return
	 */
	public int play(Snake s,Node f){
		if(f.getX()>s.getFirst().getX()&&s.getDir()!=4){
			return 6;
		}
		if(f.getX()<s.getFirst().getX()&&s.getDir()!=6){
			return 4;
		}
		if(f.getX()==s.getFirst().getX()){//X轴相等
			if(f.getY()>s.getFirst().getY()&&s.getDir()!=8)return 2;
			else if(f.getY()>s.getFirst().getY()&&s.getDir()==8){
				if(s.canMove(4))return 4;
				else if(s.canMove(6))return 6;
				else return -1;
			}
			else if(f.getY()<s.getFirst().getY()&&s.getDir()!=2)return 8;
			else if(f.getY()<s.getFirst().getY()&&s.getDir()==2){
				if(s.canMove(4))return 4;
				else if(s.canMove(6))return 6;
				else return -1;
			}
		}
		
		if(f.getY()>s.getFirst().getY()&&s.getDir()!=2){
			return 8;
		} 
		if(f.getY()==s.getFirst().getY()){
			if(f.getX()>s.getFirst().getX()&&s.getDir()!=4)return 6;
			else if(f.getX()>s.getFirst().getX()&&s.getDir()==4){
				if(s.canMove(8))return 8;
				else if(s.canMove(2))return 2;
				else return -1;
			}
			else if(f.getX()<s.getFirst().getX()&&s.getDir()!=6)return 4;
			else if(f.getX()<s.getFirst().getX()&&s.getDir()==6){
				if(s.canMove(8))return 8;
				else if(s.canMove(2))return 2;
				else return -1;
			}
		}
		if(f.getY()<s.getFirst().getY()&&s.getDir()!=8){
			return 2;
		}
		return -1;
	}
	/**
	 * Make it right；
	 * BFS,不过当蛇去吃完后发现把自己围死就GG了
	 * @param s 蛇
	 * @param f 目标,这里目标可能不是食物所以单独做参数
	 * @return  能到的话返回去的路径第一步，不能的话返回-1；
	 */
	
	public int play1(Snake s,Node f) {
		Queue<Node> q = new LinkedList<Node>();
		Set<String> vis = new HashSet<String>();// 记录访问过的节点
		Map<String, String> path = new HashMap<String, String>();//记录访问的路径,后来用A*算法在Node添加了father节点，这个可以去掉了
		Stack<String> stack = new Stack<String>();//蛇去吃的路径
		
		q.add(s.getFirst());
		while (!q.isEmpty()) {
			Node n = q.remove();
			if (n.getX() == f.getX() && n.getY() == f.getY()) {
			//如果搜到了食物，开始解析路径，因为是从后添加，所以用栈倒回来
				String state = f.toString();
				while (state != null &&!state.equals(s.getFirst().toString())) {
					stack.push(state);
					state = path.get(state);
				}
				
				String []str;
				//有时候食物和头挨着就会导致栈为空
				if(stack.isEmpty()){
					str = state.split("-");
				}else  str = stack.peek().split("-");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				if (x > s.getFirst().getX() && y == s.getFirst().getY()) {
					return 6;
				}
				if (x < s.getFirst().getX() && y == s.getFirst().getY()) {
					return 4;
				}
				if (x == s.getFirst().getX() && y > s.getFirst().getY()) {
					return 2;
				}
				if (x == s.getFirst().getX() && y < s.getFirst().getY()) {
					return 8;
				}
			}
			Node up = new Node(n.getX(), n.getY() - Snake.size);
			Node right = new Node(n.getX() + Snake.size, n.getY());
			Node down = new Node(n.getX(), n.getY() + Snake.size);
			Node left = new Node(n.getX() - Snake.size, n.getY());
			if (!s.getMap().contains(up.toString()) && !vis.contains(up.toString()) 
					&& up.getX() <= Snake.map_size&& up.getX() >= 10 
					&& up.getY() <= Snake.map_size && up.getY() >= 10) {
				q.add(up);
				vis.add(up.toString());
				path.put(up.toString(),n.toString());
			}
			if (!s.getMap().contains(right.toString()) && !vis.contains(right.toString())
					&& right.getX() <= Snake.map_size&& right.getX() >= 10 
					&& right.getY() <= Snake.map_size && right.getY() >= 10) {
				q.add(right);
				vis.add(right.toString());
				path.put(right.toString(),n.toString());
			}
			if (!s.getMap().contains(down.toString()) && !vis.contains(down.toString()) 
					&& down.getX() <= Snake.map_size&& down.getX() >= 10 
					&& down.getY() <= Snake.map_size && down.getY() >= 10) {
				q.add(down);
				vis.add(down.toString());
				path.put(down.toString(),n.toString());
			}
			if (!s.getMap().contains(left.toString()) && !vis.contains(left.toString()) 
					&& left.getX() <= Snake.map_size&& left.getX() >= 10 
					&& left.getY() <= Snake.map_size && left.getY() >= 10) {
				q.add(left);
				vis.add(left.toString());
				path.put(left.toString(),n.toString());
			}
		}
		return -1;
	}
	/**
	 * Make it right+   //make it fast有点难
	 * 
	 * 如果不可以吃食物就追尾巴，可以吃就先派一条虚拟蛇去吃，如果吃到食物后还可以去追尾巴那就去吃，否者先追尾巴，直到去吃食物后也是安全的,就去吃。
	 * 如果不可以吃食物也不能追尾巴就随机走，这应该是小概率事件
	 * @param s 蛇
	 * @param f 目标
	 * @return 方向
	 */

	public int play2(Snake snake,Node f){
		Snake virSnake =new Snake(snake.getFirst(),snake.getLast(),snake.getFood(),snake.getTail());
		virSnake.setS((ArrayList<Node>) snake.getS().clone());
		virSnake.setMap((HashSet<String>) snake.getMap().clone());

		//真蛇去吃食物的方向
		int realGoTofoodDir=play1(snake,f);
		//如果吃得到食物
		if(realGoTofoodDir!=-1){
			 //派虚拟蛇去吃
			while(!virSnake.getFirst().toString().equals(f.toString())){
				virSnake.move(play1(virSnake, f));
			}
			 //虚拟蛇到尾巴去的方向
				int goToDailDir=Asearch(virSnake,virSnake.getTail());
				 //如果虚拟蛇吃完能去尾巴，真蛇就去吃
				if(goToDailDir!=-1)return realGoTofoodDir;
				else {
					snake.c++;
					/**
					 * 如果吃到后不能去自己尾巴，就跟着蛇尾跑
					 * 这里可能无限跟着蛇尾跑，主要原因是我追尾巴也用的BFS，追尾巴应该走最远距离
					 * 最远距离可以用A*算法，也就是bfs加权值贪心
					 */
					if(snake.c<100)return Asearch(snake,snake.getTail());
					else {
//						System.out.println("ok");
						return realGoTofoodDir;//直接去吃算了
					}
				}
		}else{// 如果吃不到食物
			 //真蛇到尾巴去的方向
			int realGoToDailDir=Asearch(snake,snake.getTail());
			if(realGoToDailDir==-1){
				 // 如果吃不了食物也到不了尾巴就随机走（听天由命）
				realGoToDailDir=randomDir();
				int i=0;
				while(!snake.canMove(realGoToDailDir)){
					//这里可能死循环，四面都不能走
					realGoToDailDir=randomDir();
					i++;
					if(i>300)return -1;//防死循环，只能GG了
				}
				return realGoToDailDir;
			}
			return realGoToDailDir;
		}
	}
	/**
	 * A*找最远路径
	 * @param s
	 * @param f
	 * @return
	 */
	public int Asearch(Snake s,Node f){
		ArrayList<Node> openList = new ArrayList<Node>();
		ArrayList<Node> closeList = new ArrayList<Node>();
		Stack<Node> stack = new Stack<Node>();//蛇去吃的路径
		openList.add(s.getFirst());// 将开始节点放入开放列表;
		s.getFirst().setH(dis(s.getFirst(),f));

		while(!openList.isEmpty()){
			Node now=null;
			int max=-1;
			for(Node n:openList){//我们找F值最大的(说明离目标最远),如果有相同我们选的排在后面的也就是最新添加的。
				if(n.getF()>=max){
					max=n.getF();
					now=n;
				}
			}
			// 把当前节点从开放列表删除, 加入到封闭列表;
			openList.remove(now);
			closeList.add(now);
			//四个方向的相邻节点
			Node up = new Node(now.getX(), now.getY() - Snake.size);
			Node right = new Node(now.getX() + Snake.size, now.getY());
			Node down = new Node(now.getX(), now.getY() + Snake.size);
			Node left = new Node(now.getX() - Snake.size, now.getY());
			ArrayList<Node> temp = new ArrayList<Node>(4);
			temp.add(up);
			temp.add(right);
			temp.add(down);
			temp.add(left);
			for (Node n : temp){
				// 如果该相邻节点不可通行或者该相邻节点已经在封闭列表中,则什么操作也不执行,继续检验下一个节点;
				if (s.getMap().contains(n.toString()) || closeList.contains(n)
						|| n.getX() > Snake.map_size|| n.getX() < 10 
						|| n.getY() > Snake.map_size || n.getY() < 10)
					continue;
				
				// 如果该相邻节点不在开放列表中,则将该节点添加到开放列表中,
				// 并将该相邻节点的父节点设为当前节点,同时保存该相邻节点的G和H值,F值的计算直接我写在了Node类里
				if(!openList.contains(n)){
//					System.out.println("ok");
					n.setFather(now);
					n.setG(now.getG()+10);
					n.setH(dis(n,f));
					openList.add(n);
					// 当终点节点被加入到开放列表作为待检验节点时, 表示路径被找到,此时终止循环,返回方向;
					if (n.equals(f)) {
						
						//从目标节点往前找，....卧槽这里有个坑，node不能用f，因为f与找到的节点虽然坐标相同但f没有记录father
						Node node = openList.get(openList.size() - 1);
						while(node!=null&&!node.equals(s.getFirst())){
							stack.push(node);
							node=node.getFather();
						}
						int x = stack.peek().getX();
						int y = stack.peek().getY();
						if (x > s.getFirst().getX() && y == s.getFirst().getY()) {
							return 6;
						}
						if (x < s.getFirst().getX() && y == s.getFirst().getY()) {
							return 4;
						}
						if (x == s.getFirst().getX() && y > s.getFirst().getY()) {
							return 2;
						}
						if (x == s.getFirst().getX() && y < s.getFirst().getY()) {
							return 8;
						}
					}
				}
				// 如果该相邻节点在开放列表中,
				// 则判断若经由当前节点到达该相邻节点的G值是否大于或小于(这里找最远用大于)原来保存的G值,若大于或小于,则将该相邻节点的父节点设为当前节点,并重新设置该相邻节点的G和F值.
				if (openList.contains(n)) {
					if (n.getG() > (now.getG() + 10)) {
						n.setFather(now);
						n.setG(now.getG() + 10);
					}
				}
			}
		}
		// 当开放列表为空,表明已无可以添加的新节点,而已检验的节点中没有终点节点则意味着路径无法被找到,此时也结束循环返回-1;
		return -1;
	}
	/**
	 * 计算曼哈顿距离
	 * @param src
	 * @param des
	 * @return
	 */
	public int dis(Node src,Node des){
		return Math.abs(src.getX()-des.getX())+Math.abs(src.getY()-des.getY());
	}
	/**
	 * 随机生产方向
	 * @return
	 */
	public int randomDir(){
		int dir=(int)Math.random()*4;
		if(dir==0)return 8;
		else if(dir==1)return 6;
		else if(dir==2)return 2;
		else return 4;
	}
}
