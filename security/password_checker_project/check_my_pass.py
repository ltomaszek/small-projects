# Checkes if passwords have been pawned
import sys
import requests
import hashlib

API = 'https://api.pwnedpasswords.com/range'


def request_api_data(query_char):
    # must be exactly 5 characters
    url = f'{API}/{query_char}'
    # returns the rest of all hashes from DB that match the 5 first caracters with number of occurences
    res = requests.get(url)
    if res.status_code != 200:
        raise RuntimeError(f'Error fetching: {res.status_code}')
    return res


def get_password_leaks_count(hashes:requests.models.Response, hash_to_check):
    '''Compares tail of your hast (hash_to_check) with the hashes from the response'''
    hashes = (line.split(':') for line in hashes.text.splitlines())
    for hash, code in hashes:
        if hash == hash_to_check:
            return code
    return 0


def pwned_api_check(password):
    sha1_password = hashlib.sha1(password.encode('utf-8')).hexdigest().upper()
    first5_char, tail = sha1_password[:5], sha1_password[5:]
    response = request_api_data(first5_char)
    return get_password_leaks_count(response, tail)


def main(password_list):
    for password in password_list:
        count = pwned_api_check(password)
        if count:
            print(f'Password: \'{password}\' -> was found {count} times')
        else:
            print(f'Password: \'{password}\' -> is safe')
    return 'Done!'


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))