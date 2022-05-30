package org.example.gorest.framework

class RandomGenerator {

    static firstNames = ["Joe", "Jane", "Tom", "Ted", "Carl", "Peter", "Paul", "Aidan", "Sandra", "Ann", "Anna",  "Alan", "Mary"]
    static surnames = ["Smith", "Doe", "Claus", "Berg", "West", "North", "Musk", "Gates", "Alan", "Silva", "Jameson", "Lee" ]
    static nameGenerator()
    {



        int sIndex = (Math.random() * 9364213947) % surnames.size()
        int fIndex = (Math.random() * 362431784) % firstNames.size()


        return firstNames[fIndex] + " " + surnames[sIndex]

    }

    static emailGenerator( ){

            int sIndex = (Math.random() * 3423425421) % surnames.size()
            int fIndex = (Math.random() * 76457544) % firstNames.size()

        return firstNames[fIndex] + "." + surnames[sIndex] + "@Mail.com"


    }





}
