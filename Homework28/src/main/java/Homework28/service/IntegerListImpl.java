package Homework28.service;

import Homework28.exception.ArrayIsFullException;
import Homework28.exception.ElementNullException;
import Homework28.exception.ExitForBorderArrayException;
import Homework28.exception.NotFoundElementException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class IntegerListImpl implements IntegerList {
    private static int MAXIMUM_NUMBER_OF_CELLS = 10;
    private Integer[] arrayList;
    private int size;

    public IntegerListImpl() {
        arrayList = new Integer[MAXIMUM_NUMBER_OF_CELLS];
    }

    // Добавление элемента.
    // Вернуть добавленный элемент
    // в качестве результата выполнения.
    @Override
    public Integer add(Integer item) {
        validateItem(item);
        if (size == arrayList.length) {
            arrayList = grow(arrayList);
        }
        arrayList[size++] = item;
        return item;
    }

    // Добавление элемента
    // на определенную позицию списка.
    // Если выходит за пределы фактического
    // количества элементов или массива,
    // выбросить исключение.
    // Вернуть добавленный элемент
    // в качестве результата выполнения.
    @Override
    public Integer add(int index, Integer item) {
        validateItem(item);
        validateIndex(index);
        if (size == arrayList.length) {
            arrayList = grow(arrayList);
        }
        if (index == size) {
            arrayList[size++] = item;
            return item;
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, size - index);
        arrayList[index] = item;
        size++;
        return item;
    }

    // Установить элемент
    // на определенную позицию,
    // затерев существующий.
    // Выбросить исключение,
    // если индекс больше
    // фактического количества элементов
    // или выходит за пределы массива.
    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        validateItem(item);
        arrayList[index] = item;
        return item;
    }

    // Удаление элемента.
    // Вернуть удаленный элемент
    // или исключение, если подобный
    // элемент отсутствует в списке.
    @Override
    public Integer remove(Integer item) {
        validateItem(item);
        int index = indexOf(item);
        if (index == -1) {
            throw new NotFoundElementException();
        }
        if (index != size) {
            System.arraycopy(arrayList, index + 1, arrayList, index, size - index);
        }
        size--;
        return item;
    }

    // Удаление элемента по индексу.
    // Вернуть удаленный элемент
    // или исключение, если подобный
    // элемент отсутствует в списке.
    @Override
    public Integer remove(int index) {
        validateIndex(index);
        Integer item = arrayList[index];
        if (index != size) {
            System.arraycopy(arrayList, index + 1, arrayList, index, size - index);
        }
        size--;
        return item;
    }

    // Проверка на существование элемента.
    // Вернуть true/false;
    @Override
    public boolean contains(Integer item) {
        validateItem(item);
        int end=0;
        if(arrayList[arrayList.length-1]==null){
            for (int i = arrayList.length-1; i >=0 ; i--) {
                if(arrayList[i]!=null){
                    end=i;
                    break;
                }
            }
        }

        quickSort(arrayList, 0, end);
        return contains(arrayList, item);
    }

    // Поиск элемента.
    // Вернуть индекс элемента
    // или -1 в случае отсутствия.
    @Override
    public int indexOf(Integer item) {
        validateItem(item);
        for (int i = 0; i < size; i++) {
            if (arrayList[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    // Поиск элемента с конца.
    // Вернуть индекс элемента
    // или -1 в случае отсутствия.
    @Override
    public int lastIndexOf(Integer item) {
        validateItem(item);
        for (int i = size - 1; i >= 0; i--) {
            if (arrayList[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    // Получить элемент по индексу.
    // Вернуть элемент или исключение,
    // если выходит за рамки фактического
    // количества элементов.
    @Override
    public Integer get(int index) {
        validateIndex(index);
        return arrayList[index];
    }

    // Сравнить текущий список с другим.
    // Вернуть true/false или исключение,
    // если передан null.
    @Override
    public boolean equals(Integer[] otherList) {
        return Arrays.equals(Arrays.stream(arrayList).toArray(), Arrays.stream(otherList).toArray());
    }

    // Вернуть фактическое количество элементов.
    @Override
    public int size() {
        return size;
    }

    // Вернуть true,
    // если элементов в списке нет,
    // иначе false.
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // Удалить все элементы из списка.
    @Override
    public void clear() {
        size = 0;
    }

    // Создать новый массив
    // из строк в списке
    // и вернуть его.
    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(arrayList, size);
    }

    private void validateIndex(int index) {
        if (index < 0 || index > size) {
            throw new ExitForBorderArrayException();
        }
    }

    private void validateSize() {
        if (size == arrayList.length) {
            throw new ArrayIsFullException();
        }
    }

    private void validateItem(Integer item) {
        if (item == null) {
            throw new ElementNullException();
        }
    }

    public void quickSort(Integer[] arr, int begin, int end) {
        if (arr[end] != null) {
            if (begin < end) {
                int partitionIndex = partition(arr, begin, end);

                quickSort(arr, begin, partitionIndex - 1);
                quickSort(arr, partitionIndex + 1, end);
            }
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int i=0;
        if(arr[end]!=null){
        int pivot = arr[end];
        i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                swapElements(arr, i, j);
            }
        }
        swapElements(arr, i + 1, end);}
        return i + 1;
    }

    private void swapElements(Integer[] arr, int left, int right) {
        int temp = arr[left];
        arr[left] = arr[right];
        arr[right] = temp;
    }

    private boolean contains(Integer[] arr, int element) {
        int min = 0;
        int max = arr.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (element == arr[mid]) {
                return true;
            }
            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private static Integer[] grow(Integer[] arr) {
        int count = (int) (arr.length * 1.5);
        arr = Arrays.copyOf(arr, count);
        return arr;
    }


}
