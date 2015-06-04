#include <stdio.h>
#include <stdlib.h>

struct birthday
{
    int no_of_people;
    double pval;
};

double probability(int days,int people)
{
    int i;
    double p,pbar=1;
    double daysp1=days,daysp2=days;

        for(i=1;i<people;i++)
            pbar=pbar*((daysp2*(days-i))/(daysp1*days));    // calculating pbar using ( pi i =0 to i=no of people(no of days-i)/(no of days)^ (no of people) )

        p=1-pbar;  // using (p + pbar = 1)

    return p;
}


int main()
{
    int days,people,bd=0,i,choice,noip;
    struct birthday bday[6];
    printf("\n\t\t\t\tBIRTHDAY PARADOX\n\n");
    printf("\nEnter the no days:");
    scanf("%d",&days);

    printf("\nChoice\n1.User input\n2.Random input");
    scanf("%d",&choice);
    switch(choice)
    {
    case 1:

        printf("\nEnter no of inputs (less than 6): ");
        scanf("%d",&noip);
        for(i=0;i<noip;i++)
        {
            printf("\nEnter the no of people in a room %d :",i+1);
            scanf("%d",&people);
            bday[bd].no_of_people=people;

            if(days >= people)
            {
                bday[bd].pval=probability(days,people);
                bd++;
            }
            else
            {
                bday[bd].pval=1;
                bd++;
            }
        }

        for(i=0;i<noip;i++)
            printf("\n\n\n%d \t %f",bday[i].no_of_people,bday[i].pval);

    break;

    case 2:     // taking random no
        for(i=0;i<6;i++)
        {
            people=rand()%365 + 1;  // taking random values from 1 to 365
            bday[bd].no_of_people=people;

            if(days >= people)
            {
                bday[bd].pval=probability(days,people);
                bd++;
            }
            else
            {
                bday[bd].pval=1;
                bd++;
            }
        }

        for(i=0;i<6;i++)
            printf("\n\n\n%d \t %f",bday[i].no_of_people,bday[i].pval);

    break;
    }

return 0;
}
