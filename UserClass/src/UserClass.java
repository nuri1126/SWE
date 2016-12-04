void add() throws IOException {
		BufferedReader tempContent = new BufferedReader(inputStreamReader);
		System.out.println("스케줄 내용을 입력하세요.");
		System.out.print(">>>");
		String inputContent = tempContent.readLine();
		System.out.println("스케줄 날짜를 입력하세요.(yyyy-MM-dd)");
		System.out.print(">>>");
		String inputDate = tempContent.readLine();
		
		while(true){
			System.out.println("스케줄을 추가하시겠습니까?(Y/N)");
			System.out.print(">>>");
			BufferedReader tempAnswer = new BufferedReader(inputStreamReader);
			String answer = tempAnswer.readLine();
			if(answer.equals("Y")) {
				Schedule schedule = new Schedule(inputContent, initialScheduleNumber, inputDate);
				scheduleVector.add(schedule);
				System.out.println("스케줄을 저장하였습니다!");
				updateNumber();
				break;
			} else if(answer.equals("N")){
				System.out.println("스케줄 작성을 취소합니다.");
				break;
			} else
				System.out.println("Y 또는 N를 입력해주세요!");
		}
	}
	