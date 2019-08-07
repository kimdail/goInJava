package com.study.study;

import org.junit.Test;

import java.util.Optional;

import static com.study.study.Refactoring.map;

public class OptionalStudy {

    //10.1 Ŭ���� ����ÿ� null
    @Test
    public void getCarInsuranceName() {
        Person person = new Person();
        String a =  person.getCar().getInsurance().getName();
    }
    // getCarInsuranceName �޼��� ���� �� Person�� Car�� ����ִٸ� NullPointerException �߻�

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // �����õ� 1) ���� �ǽ�
    @Test
    public void getCarInsuranceName1() {
        Person person = new Person();
        String AA = "";
        if ( person != null ) {
            Car car = person.getCar();
            if ( car != null ) {
                Insurance insurance = car.getInsurance();
                if ( insurance != null ) {
                    AA = insurance.getName();
                }
            }
        }
        AA = "Unknown";
    }
    // null Ȯ�� �ڵ� ������ ������ ȣ�� ü���� �鿩���� ������ ������
    // �̿� ���� �ݺ� ���� �ڵ带 '���� �ǽ�(deep doubt)' �̶�� �θ�

    // �����õ� 2) �ʹ� ���� �ⱸ
    @Test
    public void getCarInsuranceName2() {
        Person person = new Person();
        String BB = "";
        if( person == null ) {
            BB = "Unknown";
        }
        Car car = person.getCar();
        if ( car == null ) {
            BB = "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if ( insurance == null ) {
            BB = "Unknown";
        }
    }
    // null Ȯ�� �ڵ帶�� �ⱸ�� ����
    // ���� �ⱸ ������ ���������� �������

    //å 315 ������ ��� �ѹ� ����
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //OptionalStudy : ���������� ĸ��ȭ�ϴ� Ŭ����
    // ��) ���� �����ϰ� ���� ���� Person Ŭ������ car ������ null�� ������ ��
    //   -> ���ο� Optional�� �̿��ϸ� null�� �Ҵ��ϴ� �� ��� ���� ������ Optional<Car>�� ������.
    //      ���� �ִٸ� Optioanl Ŭ������ ���� ���ΰ�, ���� ������ Optional.empty �޼���� Optional ��ȯ
    //- Optional.empty�� Optional�� Ư���� �̱��� �ν��Ͻ��� ��ȯ�ϴ� ���� ���丮 �޼���
    //- null ���۷����� OptionalStudy.empty()
    // * null�� �����Ϸ� �ϸ� NullPointerException �߻�
    // * Optional.empty()�� Optional ��ü�̹Ƿ� �̸� �پ��� ������� Ȱ�� ����
    // * null ��� Optional�� ����ϸ鼭 Car ������ Optional<Car>�� �ٲ�
    //    -> ���� ���� �� ������ ��������� ǥ��

    //10.2 Optional Ŭ���� �Ұ�
    public class Person1 {
        private Optional<Car1> car; // ����� ���� �������� ���� ���� �ʾ��� ���� �����Ƿ� Optional
        public Optional<Car1> getCar() { return car; }
    }
    public class Car1 {
        private Optional<Insurance1> insurance; // �ڵ��� ���迡 ���� or �̰����ϼ� �����Ƿ�
        public Optional<Insurance1> getInsurance() { return insurance; }
    }
    public class Insurance1 {
        private String name; // ����ȸ�翡�� �ݵ�� �̸��� ������
        public String getName() { return name; }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //10.3 Optional ���� ����
    //10.3.1 Optional ��ü �����
    @Test
    public void OptionalPattern() {
        //Optional.empty() : �� Optional ��ü �����Ѵ�
        //Optional.of(value) : value���� null�� �ƴ� ��쿡 ����Ѵ�
        //Optional.ofNullable(value) : value���� null���� �ƴ��� Ȯ������ ���� ��쿡 ����Ѵ�

        Optional<String> optStr = Optional.empty(); //Optional.empty()�� empty Optional ��ü�� ����

        String str = "test";
        Optional<String> optStr1 = Optional.of(str);    //Optional.of()�� null�� �ƴ� ��ü�� ��� �ִ� Optional ��ü�� ����

        String nullStr = null;
        //Optional<String> optStr2 = Optional.of(nullStr); //null�� �ƴ� ��ü�� �����ϱ� ������ null�� �Ѱܼ� �����ϸ� NPE�� �߻�

        Optional<String> optStr3 = Optional.ofNullable(str);
        Optional<String> optStr4 = Optional.ofNullable(null); //empty Optional ��ü�� ��ȯ��
    }

    //10.3.2 ������ Optional�� ���� �����ϰ� ��ȯ�ϱ�
    // �̸� ������ �����ϱ� ���� insurance�� null���� Ȯ��
    @Test
    public void getOptionalMap(){
        String name = null;
        Insurance insurance = new Insurance();
        if ( insurance != null ) {
            name = insurance.getName();
            System.out.println(name);
        }

        // Optional�� map �޼��� ����
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance);
        Optional<String> name1 = optInsurance.map(Insurance::getName);

        System.out.println(name1);
    /*
     - Optional ��ü�� �ִ� ����� ������ �� �� ������ ������ �÷������� ������ �� �ִ�.
     - Optional�� ���� �����ϸ� map�� �μ��� ������ �Լ��� ���� �ٲ۴�.
     - Optional�� ��������� �ƹ� �ϵ� �Ͼ�� �ʴ´�. --> �߿�~!
    */
    }

    //10.3.3 flatMap���� Optional ��ü ����
    @Test
    public void getFlatMap(){
//        Person person = new Person();
//        Optional<Person> optPerson = Optional.of(person);
//        Optional<String> name = optPerson.map(Person::getCar)
//                                         .map(Car::getInsurance)  // ������ ����!!
//                                         .map(Insurance::getName);
    /*
     - ���� ����
      optPerson�� Ÿ���� Optional<Person> -> map �޼��� ȣ�� ����
      getCar�� Optional<Car> ��ȯ
      -> map �޼����� ����� Optional<Optional<Car>> ������ ��ø Optional ��ü ����
      -> �ذ��ϱ� ���ؼ� flatMap �޼��� ���! (��Ʈ���� flatMap�� ������ ���)
         �Լ��� �����ؼ� ������ ��� ��Ʈ���� �ϳ��� ��Ʈ������ �����Ͽ� ����ȭ��Ŵ
    */
    }

    //P.323
    //�̰Ŵ� �ҽ��� ���ذ��µ� �� ���������� ���ذ� �ȵ�...=-=
//    public String getCarInsuranceName(Optional<Person> person) {
//        return person.flatMap(Person::getCar) // Optional<Person>�� Optional<Car>�� ��ȯ
//                     .flatMap(Car::getInsurance) // Optional<Car>�� Optional<Insurance>�� ��ȯ
//                     .map(Insurance::getName) // getName�� String�� ��ȯ�ϹǷ� flatMap �ʿ� X
//                     .orElse("Unknown"); // ��� Optional�� ��������� �⺻�� ���
//    }
     //null�� Ȯ���Ϸ��� ���� �б⹮�� �߰��ؼ� �ڵ尡 ���������� ���� ���� �� ����

    //P.324 �׸� ���� ����

    // ����ȭ ���� �ʿ��� ��
    // Optional�� ���� ��ȯ���� �� �ִ� �޼��带 �߰��ϴ� ��� ���� �Ʒ��� ���� ���ּ���~! : )
    public class Person2 {
        private Car car;
        public Optional<Car> getCarAsOptional() {
            return Optional.ofNullable(car);
        }
    }

    //10.4 Optional ����
    @Test
    public void exampleOptional() {
        // Map<String, Object> ������ �ʿ��� ������ ���� key�� ���� ������ ��

        Object value = map.get("key");
        // key �� �ش��ϴ� ���� ������ null ��ȯ

        // ���1) ������ if-then-else �߰�
        // ���2) Optional.ofNullable �̿�
        Optional<Object> value1 = Optional.ofNullable(map.get("key"));  //���������� null�� �� �� �ִ� ����� Optional�� ����
    }

    // - �ڹ� API�� � �������� ���� ������ �� ���� �� null�� ��ȯ�ϴ� ��� ���ܸ� �߻���ų �� ����
    // - �̿� ���� ���� ���ڿ��� ������ ��ȯ�ϴ� Integer.parseInt(String)���� ����
    public static Optional<Integer> stringToInt(String s) {
        try {
            return Optional.of(Integer.parseInt(s)); // ��ȯ ���� �� ��ȯ�� �� �����ϴ� Optional ��ȯ
        } catch (NumberFormatException e) {
            return Optional.empty(); // �� �ܿ��� �� Optional ��ȯ
        }
    }

}
