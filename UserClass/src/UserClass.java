void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("������ ������ �Է��ϼ���.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		System.out.println("������ ��¥�� �Է��ϼ���.(yyyy-MM-dd)");
		System.out.print(">>>");
		String inputDate = tempContent.readLine();
		
		while(true){
			System.out.println("�������� �߰��Ͻðڽ��ϱ�?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
				scheduleVector.add(schedule);
				System.out.println("�������� �����Ͽ����ϴ�!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("������ �ۼ��� ����մϴ�.");
				break;
			} else
				System.out.println("Y �Ǵ� N�� �Է����ּ���!");
		}
	}
	