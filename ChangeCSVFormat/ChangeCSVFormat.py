import csv

ifile_dir = 'learningData'
ofile_dir = ifile_dir + '_out'
first_row = []

with open(ifile_dir + '.csv', mode = 'r') as csv_file:
    print('Reading {ifile_dir}.csv')
    csv_reader = csv.reader(csv_file, delimiter=',')
    line_count = 0
    first_row = next(csv_reader)
# print (first_row)


with open(ofile_dir + '.csv', mode='w') as csv_file:
    print('Writing {ofile_dir}.txt')
    fieldnames = ['property']
    csv_writer = csv.DictWriter(csv_file, fieldnames=fieldnames)
    csv_writer.writeheader()
    # csv_writer.writerows([first_row])
    for element in first_row:
        print (element)
        csv_writer.writerow({'property' : element})

